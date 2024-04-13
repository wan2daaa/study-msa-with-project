package me.wane.money.adapter.axon.saga;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import me.wane.common.event.*;
import me.wane.money.adapter.axon.event.RechargingRequestCreatedEvent;
import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.wane.money.application.port.out.IncreaseMoneyPort;
import me.wane.money.domain.MemberMoney;
import me.wane.money.domain.MemberMoney.MembershipId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.*;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@NoArgsConstructor
public class MoneyRechargeSaga {

  @NonNull
  private transient CommandGateway commandGateway;

  @Autowired
  public void setCommandGateway(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @StartSaga
  @SagaEventHandler(associationProperty = "rechargingRequestId")
  public void handle(RechargingRequestCreatedEvent event) {
    System.out.println("RechargingRequestCreatedEvent Start Saga");

    String rechargingRequestId = event.getRechargingRequestId();

    // 앞으로의 handle 동작을 위해서 어떤 것을 associationKey 로 쓸것인지 정의 한 것
    String checkRegisteredBankAccountId = UUID.randomUUID().toString();
    SagaLifecycle.associateWith("checkRegisteredBankAccountId", checkRegisteredBankAccountId);

    // "충전 요청" 이 시작 되었다!


    // 뱅킹의 계좌 등록 여부 확인하기. (RegisteredBankAccount)
    // CheckRegisteredBankAccountCommand -> Check Bank Account
    // -> axon server -> Banking Service -> Common

    // 기본적으로 Axon framework 에서, 모든 aggregate의 변경은, aggregate 단위로 되어야만 한다! (DDD)
    commandGateway.send(new CheckRegisteredBankAccountCommand(
        event.getRegisteredBankAccountAggregateIdentifier(),
        event.getRechargingRequestId(),
        event.getMembershipId(),
        checkRegisteredBankAccountId,
        event.getBankName(),
        event.getBankAccountNumber(),
        event.getAmount()
    )).whenComplete((result, throwable) -> {

      if (throwable != null) {
        throwable.printStackTrace();
        System.out.println("CheckRegisteredBankAccountCommand Error");
      } else {
        System.out.println("CheckRegisteredBankAccountCommand Success");
      }
    });

  }

  @SagaEventHandler(associationProperty = "checkRegisteredBankAccountId")
  public void handle(CheckedRegisteredBankAccountEvent event) {
    System.out.println("CheckedRegisteredBankAccountEvent Saga: "+ event.toString());

    boolean status = event.isChecked();
    if (status) {
      System.out.println("CheckedRegisteredBankAccountEvent Success");
    } else {
      System.out.println("CheckedRegisteredBankAccountEvent Error");
    }

    String requestFirmbankingId = UUID.randomUUID().toString();
    SagaLifecycle.associateWith("requestFirmbankingId", requestFirmbankingId);

    // 송금 요청
    commandGateway.send(new RequestFirmbankingCommand(
        requestFirmbankingId,
        event.getFirmbankingRequestAggregateIdentifier(),
        event.getRechargingRequestId(),
        event.getMembershipId(),
        event.getFromBankName(),
        event.getFromBankAccountNumber(),
        "wane-bank",
        "1234123412341234",
        event.getAmount()
    ));

  }

  @SagaEventHandler(associationProperty = "requestFirmbankingId")
  public void handle(RequestFirmbankingFinishedEvent event, IncreaseMoneyPort increaseMoneyPort) {
    System.out.println("RequestFirmbankingFinishedEvent Saga: "+ event.toString());
    boolean status = event.getStatus() == 0;

    if (status) {
      System.out.println("RequestFirmbankingFinishedEvent Success");
    } else {
      System.out.println("RequestFirmbankingFinishedEvent Error");
    }

    // DB Update 명령
    MemberMoneyJpaEntity resultEntity = increaseMoneyPort.increaseMoney(
        new MembershipId(event.getMembershipId()),
        event.getMoneyAmount()
    );

//    MemberMoneyJpaEntity resultEntity = null; // 강제 롤백 케이스 테스트

    if (resultEntity == null) {
      // 실패 시, 롤백 이벤트
      String rollbackFirmbankingId = UUID.randomUUID().toString();
      SagaLifecycle.associateWith("rollbackFirmbankingId", rollbackFirmbankingId);
      commandGateway.send(new RollbackFirmbankingRequestCommand(
          rollbackFirmbankingId
          ,event.getRequestFirmbankingAggregateIdentifier()
          , event.getRechargingRequestId()
          , event.getMembershipId()
          , event.getToBankName()
          , event.getToBankAccountNumber()
          , event.getMoneyAmount()
      )).whenComplete(
          (result, throwable) -> {
            if (throwable != null) {
              throwable.printStackTrace();
              System.out.println("RollbackFirmbankingRequestCommand Command failed");
            } else {
              System.out.println("Saga success : "+ result.toString());
              SagaLifecycle.end();
            }
          }
      );
    } else {
      // 성공 시, saga 종료.
      SagaLifecycle.end();
    }
  }

  @EndSaga
  @SagaEventHandler(associationProperty = "rollbackFirmbankingId")
  public void handle(RollbackFirmbankingFinishedEvent event) {
    System.out.println("RollbackFirmbankingFinishedEvent Saga: "+ event.toString());
  }

}
