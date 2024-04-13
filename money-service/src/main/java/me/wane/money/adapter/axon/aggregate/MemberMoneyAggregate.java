package me.wane.money.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;
import me.wane.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import me.wane.money.adapter.axon.command.MemberMoneyCreatedCommand;
import me.wane.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import me.wane.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import me.wane.money.adapter.axon.event.MemberMoneyCreatedEvent;
import me.wane.money.adapter.axon.event.RechargingRequestCreatedEvent;
import me.wane.money.application.port.out.GetRegisteredBankAccountPort;
import me.wane.money.application.port.out.RegisteredBankAccountAggregateIdentifier;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Data
public class MemberMoneyAggregate {

  @AggregateIdentifier
  private String id;

  private Long membershipId;

  private int balance;

  @CommandHandler
  public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
    System.out.println("memberMoneyCreatedCommand Handler");

    apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
  }

  @CommandHandler
  public String handle(@NotNull IncreaseMemberMoneyCommand command) {
    System.out.println("IncreaseMemberMoneyCommand Handler");
    id = command.getAggregateIdentifier();

    //store event
    apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
    return id;
  }

  @CommandHandler
  public void handler(
      RechargingMoneyRequestCreateCommand command,
      GetRegisteredBankAccountPort getRegisteredBankAccountPort // 뒤의 부분은 알아서 의존성 주입
  ) {
    System.out.println("RechargingMoneyRequestCreateCommand Handler");
    id = command.getAggregateIdentifier();

    // Saga Start 해줘야함
    // new RechargingRequestCreatedEvent
    // 뱅킹 정보가 필요함 -> 하지만 이 서비스는 bank 서비스 라서, -> bank service (get RegisteredBankAccount) 를 위한 Port 를 생성하고 사용
    RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier
        = getRegisteredBankAccountPort.getRegisteredBankAccount(command.getMembershipId());

    apply(new RechargingRequestCreatedEvent(
        command.getRechargingRequestId(),
        command.getMembershipId(),
        command.getAmount(),
        registeredBankAccountAggregateIdentifier.getAggregateIdentifier(),
        registeredBankAccountAggregateIdentifier.getBankName(),
        registeredBankAccountAggregateIdentifier.getBankAccountNumber()
    ));

  }

  @EventSourcingHandler
  public void on(MemberMoneyCreatedEvent event) {
    System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
    id = UUID.randomUUID().toString();
    membershipId = Long.parseLong(event.getMembershipId());
    balance = 0;
  }

  @EventSourcingHandler
  public void on(IncreaseMemberMoneyEvent event) {
    System.out.println("IncreaseMemberMoneyEvent Sourcing Handler");
    id = event.getAggregateIdentifier();
    membershipId = Long.parseLong(event.getTargetMembershipId());
    balance = event.getAmount();
  }

  public MemberMoneyAggregate() {
  }
}
