package me.wane.banking.adapter.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.in.GetRegisteredBankAccountCommand;
import me.wane.banking.application.port.out.GetRegisteredBankAccountPort;
import me.wane.banking.application.port.out.RegisterBankAccountPort;
import me.wane.banking.domain.RegisteredBankAccount.*;
import me.wane.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, GetRegisteredBankAccountPort {

  private final SpringDataRegisteredBankAccountRepository bankAccountRepository;


  @Override
  public RegisteredBankAccountJpaEntity createRegisteredBankAccount(MembershipId membershipId,
      BankName bankName, BankAccountNumber bankAccountNumber,
      LinkedStatusIsValid linkedStatusIsValid, AggregateIdentifier aggregateIdentifier) {

    return bankAccountRepository.save(
        new RegisteredBankAccountJpaEntity(
            membershipId.getMembershipId(),
            bankName.getBankName(),
            bankAccountNumber.getBankAccountNumber(),
            linkedStatusIsValid.isLinkedStatusIsValid(),
            aggregateIdentifier.getAggregateIdentifier()
        )
    );
  }

  @Override
  public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
    List<RegisteredBankAccountJpaEntity> entityList = bankAccountRepository.findByMembershipId(
        command.getMembershipId());
    if (entityList.isEmpty()) {
      return null;
    }

    return entityList.get(0);
  }
}
