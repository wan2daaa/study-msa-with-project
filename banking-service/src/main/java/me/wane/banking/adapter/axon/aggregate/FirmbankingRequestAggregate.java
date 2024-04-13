package me.wane.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wane.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import me.wane.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import me.wane.banking.adapter.axon.event.FirmbankingRequestCreateEvent;
import me.wane.banking.adapter.axon.event.FirmbankingRequestUpdatedEvent;
import me.wane.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.wane.banking.adapter.out.external.bank.FirmbankingResult;
import me.wane.banking.application.port.out.RequestExternalFirmbankingPort;
import me.wane.banking.application.port.out.RequestFirmbankingPort;
import me.wane.banking.domain.FirmbankingRequest;
import me.wane.banking.domain.FirmbankingRequest.*;
import me.wane.common.event.*;
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

  @CommandHandler
  public FirmbankingRequestAggregate(
      RequestFirmbankingCommand command,
      RequestFirmbankingPort firmbankingPort,
      RequestExternalFirmbankingPort externalFirmbankingPort
  ) {
    System.out.println("RequestFirmbankingCommand Handler");
    id = command.getAggregateIdentifier();

    // from -> to
    //db save!
    firmbankingPort.createFirmbankingRequest(
        new FromBankName(command.getFromBankName()),
        new FromBankAccountNumber(command.getFromBankAccountNumber()),
        new ToBankName(command.getToBankName()),
        new ToBankAccountNumber(command.getToBankAccountNumber()),
        new MoneyAmount(command.getMoneyAmount()),
        new FirmbankingStatus(0),
        new FirmbankingRequest.AggregateIdentifier(id)
    );

    // firmbanking 수행!
    FirmbankingResult firmbankingResult = externalFirmbankingPort.requestExternalFirmbanking(
        new ExternalFirmbankingRequest(
            command.getFromBankName(),
            command.getFromBankAccountNumber(),
            command.getToBankName(),
            command.getToBankAccountNumber(),
            command.getMoneyAmount()
        )
    );

    int resultCode = firmbankingResult.getResultCode();

    // 0. 성공, 1. 실패
    apply(new RequestFirmbankingFinishedEvent(
        command.getRequestFirmbankingId(),
        command.getRechargeRequestId(),
        command.getMembershipId(),
        command.getToBankName(),
        command.getToBankAccountNumber(),
        command.getMoneyAmount(),
        resultCode,
        id
    ));

  }

  @CommandHandler
  public FirmbankingRequestAggregate(@NotNull RollbackFirmbankingRequestCommand command, RequestFirmbankingPort firmbankingPort, RequestExternalFirmbankingPort externalFirmbankingPort) {
    System.out.println("RollbackFirmbankingRequestCommand Handler");
    id = UUID.randomUUID().toString();

    // rollback 수행 (-> 법인 계좌 -> 고객 계좌 펌뱅킹)
    firmbankingPort.createFirmbankingRequest(
        new FirmbankingRequest.FromBankName("wane"),
        new FirmbankingRequest.FromBankAccountNumber("1234123412341234"),
        new FirmbankingRequest.ToBankName(command.getBankName()),
        new FirmbankingRequest.ToBankAccountNumber(command.getBankAccountNumber()),
        new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
        new FirmbankingRequest.FirmbankingStatus(0),
        new FirmbankingRequest.AggregateIdentifier(id));

    // firmbanking!
    FirmbankingResult result = externalFirmbankingPort.requestExternalFirmbanking(
        new ExternalFirmbankingRequest(
            "wane",
            "1234123412341234",
            command.getBankName(),
            command.getBankAccountNumber(),
            command.getMoneyAmount()
        ));

    int res = result.getResultCode(); // 정상이라고 가정해서 따로 handling 안함

    apply(new RollbackFirmbankingFinishedEvent(
        command.getRollbackFirmbankingId(),
        command.getMembershipId(),
        id)
    );
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
