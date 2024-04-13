package me.wane.money.application.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.wane.common.*;
import me.wane.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import me.wane.money.adapter.axon.command.MemberMoneyCreatedCommand;
import me.wane.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import me.wane.money.adapter.axon.event.RechargingRequestCreatedEvent;
import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.wane.money.adapter.out.persistence.MoneyChangingRequestMapper;
import me.wane.money.application.port.in.*;
import me.wane.money.application.port.out.*;
import me.wane.money.domain.MemberMoney;
import me.wane.money.domain.MemberMoney.MembershipId;
import me.wane.money.domain.MemberMoney.MoneyAggregateIdentifier;
import me.wane.money.domain.MoneyChangingRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;

@RequiredArgsConstructor
@Transactional
@UseCase
public class ChangeMoneyRequestService implements IncreaseMoneyRequestUseCase,
    DecreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

  private final IncreaseMoneyPort increaseMoneyPort;
  private final DecreaseMoneyPort decreaseMoneyPort;
  private final GetMoneyPort getMoneyPort;
  private final MoneyChangingRequestMapper mapper;
  private final GetMembershipPort getMembershipPort;
  private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
  private final CountDownLatchManager countDownLatchManager;

  private final CommandGateway commandGateway;
  private final CreateMemberMoneyPort createMemberMoneyPort;
  private final GetMemberMemberMoneyPort getMemberMemberMoneyPort;

  @Override
  public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

    // 머니의 충전.증액이라는 과정
    // 1. 고객 정보가 정상인지 확인 (멤버)
    getMembershipPort.getMembership(command.getTargetMembershipId());

    // 2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

    // 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

    // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성한다. (MoneyChangingRequest)

    // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)

    // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
    // 성공 시에 멤버의 MemberMoney 값 증액이 필요해요
    MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
        new MemberMoney.MembershipId(command.getTargetMembershipId())
        , command.getAmount());

    if (memberMoneyJpaEntity != null) {
      return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
              new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
              new MoneyChangingRequest.MoneyChangingType(1),
              new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
              new MoneyChangingRequest.MoneyChangingStatus(1),
              new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
          )
      );
    }

    // 6-2. 결과가 실패라면, 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴
    return null;
  }

  @Override
  public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

    // 지금 가정한 Subtask 란
    // 각 서비스에 특정 membershipId로 Validation 하기 위한 Task

    // 1. Subtask, Task
    SubTask validMemberTask = SubTask.builder()
        .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
        .membershipID(command.getTargetMembershipId())
        .taskType("membership")
        .status("ready")
        .build();

    // 가정 : Banking Sub Task
    // Banking Account Validation
    SubTask validBankingAccountTask = SubTask.builder()
        .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
        .membershipID(command.getTargetMembershipId())
        .taskType("membership")
        .status("ready")
        .build();
    // Amount Money Firmbanking -> 무조건 OK 받았다고 가정.

    ArrayList<SubTask> subTaskList = new ArrayList<>();
    subTaskList.add(validMemberTask);
    subTaskList.add(validBankingAccountTask);

    RechargingMoneyTask task = RechargingMoneyTask.builder()
        .taskID(UUID.randomUUID().toString())
        .taskName("Increase Money Task / 머니 충전 Task")
        .subTaskList(subTaskList)
        .moneyAmount(command.getAmount())
        .membershipID(command.getTargetMembershipId())
        .toBankName("KB")
        .build();

    // 2. Kafka Cluster Produce
    // Task Produce
    sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
    countDownLatchManager.addCountDownLatch(task.getTaskID());

    // 3. Wait
    try {
      countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    // 3-1. task-consumer
    // 등록된 sub-task를 status 가 모두 OK 라면 -> task 결과를 Produce

    // 4. Task Result Consume
    // 받은 응답을 다시, countDownLatchmanager를 통해서 결과 데이터를 받아야 함
    String result = countDownLatchManager.getDataForKey(task.getTaskID());
    if (result.equals("success")) {
      // 4-1. Consume ok, Logic
      MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
          new MemberMoney.MembershipId(command.getTargetMembershipId())
          , command.getAmount());

      if (memberMoneyJpaEntity != null) {
        return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                new MoneyChangingRequest.MoneyChangingType(1),
                new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                new MoneyChangingRequest.MoneyChangingStatus(1),
                new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
            )
        );
      }

    } else {
      // 4-2. Consume fail, Logic
      return null;
    }

    // 5. Consume 이 Ok 된 이후, 추가 비즈니스 로직

    return null;
  }


  @Override
  public MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyRequestCommand command) {
    // 머니의 충전.증액이라는 과정
    // 1. 고객 정보가 정상인지 확인 (멤버)
    MembershipStatus membership = getMembershipPort.getMembership(command.getTargetMembershipId());
    if (!membership.isValid()) {
      return null;
    }

    // 2. 고객의 잔액이 충분한지 확인
    MemberMoneyJpaEntity memberMoneyEntity = getMoneyPort.findMemberMoneyByMembershipId(
        command.getTargetMembershipId());

    if (memberMoneyEntity.getBalance() < command.getAmount()) {
      // 2-1. 고객의 잔액이 충분하지 않으면 고객의 연동된 계좌의 잔액이 충분한지 확인
      // 2-1-1. 연동된 계좌의 잔액이 충분하면 충전 만약 빠지는 금액이 10000원 이하면, 10000원을 빼고, 그이상은 그 금액에 맞춰서 가지고 오기

      // 3 번으로 이동

      // 2-2-2 고객의 연동된 잔액이 충분하지 않으면 throw error
    }

    // 3. 고객의 머니 빼기
    decreaseMoneyPort.decreaseMoney(
        command.getTargetMembershipId(),
        command.getAmount()
    );

    // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성한다. (MoneyChangingRequest)
    return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
            new MoneyChangingRequest.MoneyChangingType(1),
            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
            new MoneyChangingRequest.MoneyChangingStatus(1),
            new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
        )
    );
  }

  @Override
  public void createMemberMoney(CreateMemberMoneyCommand command) {
    MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getTargetMembershipId());
    commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
      if (throwable != null) {
        System.out.println("throwable = " + throwable);
        ;
        throw new RuntimeException(throwable);
      } else {
        System.out.println("result = " + result);
        createMemberMoneyPort.createMemberMoney(
            new MembershipId(command.getTargetMembershipId()),
            new MoneyAggregateIdentifier(result.toString())
        );
      }
    });


  }

  @Override
  public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {
    MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMemberMoneyPort.getMemberMoney(
        new MembershipId(command.getTargetMembershipId()));
    String memberMoneyAggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();

    // Saga의 시작을 나타내는 Command
    // RechargingMoneyRequestCreateCommand // membermoney의 정보를 바꾸는 커맨드
    commandGateway.send(new RechargingMoneyRequestCreateCommand(
        memberMoneyAggregateIdentifier,
        UUID.randomUUID().toString(),
        command.getTargetMembershipId(),
        command.getAmount()
    )).whenComplete((result, throwable) -> {
      if (throwable != null) {
        System.out.println("throwable = " + throwable);
        throw new RuntimeException(throwable);
      } else {
        System.out.println("result = " + result);
//        increaseMoneyPort.increaseMoney(
//            new MemberMoney.MembershipId(command.getTargetMembershipId())
//            , command.getAmount());
      }
    });




//    MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMemberMoneyPort.getMemberMoney(
//        new MembershipId(command.getTargetMembershipId())
//    );
//
//    // command
//    commandGateway.send(IncreaseMemberMoneyCommand.builder()
//            .aggregateIdentifier(memberMoneyJpaEntity.getAggregateIdentifier())
//            .membershipId(command.getTargetMembershipId())
//            .amount(command.getAmount())
//            .build()
//        )
//        .whenComplete((result, throwable) -> {
//          if (throwable != null) {
//            System.out.println("throwable = " + throwable);
//            throw new RuntimeException(throwable);
//          } else {
//            // Increase money 이벤트 소싱 완료 -> money increasing event
//            increaseMoneyPort.increaseMoney(
//                new MemberMoney.MembershipId(command.getTargetMembershipId())
//                , command.getAmount());
//          }
//        });

  }
}
