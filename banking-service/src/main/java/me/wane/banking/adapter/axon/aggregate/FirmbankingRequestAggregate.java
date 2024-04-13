package me.wane.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wane.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import me.wane.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import me.wane.banking.adapter.axon.event.FirmbankingRequestCreateEvent;
import me.wane.banking.adapter.axon.event.FirmbankingRequestUpdatedEvent;
import me.wane.banking.application.port.in.RequestFirmbankingCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Data
@NoArgsConstructor
public class FirmbankingRequestAggregate {

  @AggregateIdentifier
  private String id;

  private String fromBankName;
  private String fromBankAccountNumber;

  private String toBankName;
  private String toBankAccountNumber;

  private int moneyAmount;

  private int firmbankingStatus;


  @CommandHandler
  public FirmbankingRequestAggregate(CreateFirmbankingRequestCommand command) {
    System.out.println("CreateFirmbankingRequestCommand Handler");

    apply(new FirmbankingRequestCreateEvent(
        command.getFromBankName(),
        command.getFromBankAccountNumber(),
        command.getToBankName(),
        command.getToBankAccountNumber(),
        command.getMoneyAmount()
    ));
  }

  @CommandHandler
  public String handle(UpdateFirmbankingRequestCommand command) {
    System.out.println("UpdateFirmbankingRequestCommand Handler");
    id = command.getFirmbankingRequestAggregateIdentifier();

    apply(new FirmbankingRequestUpdatedEvent(
        command.getStatus()
    ));
    return id;
  }

  @EventSourcingHandler
  public void on(FirmbankingRequestCreateEvent event) {
    System.out.println("FirmbankingRequestCreateEvent Sourcing Handler");

    id = UUID.randomUUID().toString();
    fromBankName = event.getFromBankName();
    fromBankAccountNumber = event.getFromBankAccountNumber();
    toBankName = event.getToBankName();
    toBankAccountNumber = event.getToBankAccountNumber();
    moneyAmount = event.getMoneyAmount();
  }

  @EventSourcingHandler
  public void on(FirmbankingRequestUpdatedEvent event) {
    System.out.println("FirmbankingRequestUpdatedEvent Sourcing Handler");

    firmbankingStatus = event.getFirmbankingStatus();
  }


}
