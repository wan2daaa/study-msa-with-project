package me.wane.banking.application.port.out;

import me.wane.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.wane.banking.domain.RegisteredBankAccount.*;

public interface RegisterBankAccountPort {

  RegisteredBankAccountJpaEntity createRegisteredBankAccount(
      MembershipId membershipId,
      BankName bankName,
      BankAccountNumber bankAccountNumber,
      LinkedStatusIsValid linkedStatusIsValid,
      AggregateIdentifier aggregateIdentifier);


}
