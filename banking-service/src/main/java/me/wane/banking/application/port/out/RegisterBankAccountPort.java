package me.wane.banking.application.port.out;

import me.wane.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.wane.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountPort {

  RegisteredBankAccountJpaEntity createRegisteredBankAccount(
      RegisteredBankAccount.MembershipId membershipId,
      RegisteredBankAccount.BankName bankName,
      RegisteredBankAccount.BankAccountNumber bankAccountNumber,
      RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid
  );


}
