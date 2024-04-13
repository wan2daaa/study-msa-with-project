package me.wane.banking.adapter.axon.aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.NoArgsConstructor;
import me.wane.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import me.wane.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import me.wane.banking.adapter.out.external.bank.BankAccount;
import me.wane.banking.adapter.out.external.bank.GetBankAccountRequest;
import me.wane.banking.application.port.out.RequestBankAccountInfoPort;
import me.wane.common.event.CheckRegisteredBankAccountCommand;
import me.wane.common.event.CheckedRegisteredBankAccountEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class RegisteredBankAccountAggregate {

  @AggregateIdentifier
  private String id;

  private String membershipId;

  private String bankName;

  private String bankAccountNumber;

  @CommandHandler
  public RegisteredBankAccountAggregate(@NotNull CreateRegisteredBankAccountCommand command) {
    System.out.println("CreateRegisteredBankAccountCommand Handler");

    apply(new CreateRegisteredBankAccountEvent(
        command.getMembershipId(),
        command.getBankName(),
        command.getBankAccountNumber()));
  }

  @CommandHandler
  public void handle(@NotNull CheckRegisteredBankAccountCommand command,
      RequestBankAccountInfoPort requestBankAccountInfoPort) {
    System.out.println("CheckRegisteredBankAccountCommand Handler");

    // 이 command를 통해, 이 어그리거트 (RegisteredBankAccount)가 정상인지를 확인
    id = command.getAggregateIdentifier();

    // 실제로 체크 로직 ! RegisteredBankAccount
    BankAccount account = requestBankAccountInfoPort.getBankAccountInfo(
        new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber())
    );

    boolean isValid = account.isValid();

    // 펌뱅킹 시작
    String firmbankingUUID = UUID.randomUUID().toString();

    //CheckedRegisteredBankAccountEvent 법인계좌에서 -> 고객 통장으로 입금
    apply(new CheckedRegisteredBankAccountEvent(
        command.getRechargeRequestId(),
        command.getCheckRegisteredBankAccountId(),
        command.getMembershipId(),
        isValid,
        command.getAmount(),
        firmbankingUUID,
        account.getBankName(),
        account.getBankAccountNumber()
    ));

//    command.get()

  }

  @EventSourcingHandler
  public void on(CreateRegisteredBankAccountEvent event) {
    System.out.println("CreateRegisteredBankAccountEvent Sourcing Handler");

    id = UUID.randomUUID().toString();
    membershipId = event.getMembershipId();
    bankName = event.getBankName();
    bankAccountNumber = event.getBankAccountNumber();
  }

}
