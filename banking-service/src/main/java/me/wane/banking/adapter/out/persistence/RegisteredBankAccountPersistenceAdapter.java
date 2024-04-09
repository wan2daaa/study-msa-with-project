package me.wane.banking.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.out.RegisterBankAccountPort;
import me.wane.banking.domain.RegisteredBankAccount.BankAccountNumber;
import me.wane.banking.domain.RegisteredBankAccount.BankName;
import me.wane.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import me.wane.banking.domain.RegisteredBankAccount.MembershipId;
import me.wane.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort {

  private final SpringDataRegisteredBankAccountRepository bankAccountRepository;


  @Override
  public RegisteredBankAccountJpaEntity createRegisteredBankAccount(MembershipId membershipId,
      BankName bankName, BankAccountNumber bankAccountNumber,
      LinkedStatusIsValid linkedStatusIsValid) {

    return bankAccountRepository.save(
        new RegisteredBankAccountJpaEntity(
            membershipId.getMembershipId(),
            bankName.getBankName(),
            bankAccountNumber.getBankAccountNumber(),
            linkedStatusIsValid.isLinkedStatusIsValid()
        )
    );
  }
}
