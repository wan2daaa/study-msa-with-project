package me.wane.money.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;
import me.wane.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import me.wane.money.adapter.axon.command.MemberMoneyCreatedCommand;
import me.wane.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import me.wane.money.adapter.axon.event.MemberMoneyCreatedEvent;
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
