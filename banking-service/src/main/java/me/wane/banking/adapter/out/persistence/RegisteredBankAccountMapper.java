package me.wane.banking.adapter.out.persistence;

import me.wane.banking.domain.RegisteredBankAccount;
import me.wane.banking.domain.RegisteredBankAccount.MembershipId;
import me.wane.banking.domain.RegisteredBankAccount.RegisteredBankAccountId;
import org.springframework.stereotype.Component;

@Component
public class RegisteredBankAccountMapper {

  public RegisteredBankAccount mapToDomainEntity(RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity) {
    return RegisteredBankAccount.generateMember(
        new RegisteredBankAccountId(registeredBankAccountJpaEntity.getRegisteredBankAccountId() +""),
        new MembershipId(registeredBankAccountJpaEntity.getMembershipId()),
        new me.wane.banking.domain.RegisteredBankAccount.BankName(registeredBankAccountJpaEntity.getBankName()),
        new me.wane.banking.domain.RegisteredBankAccount.BankAccountNumber(registeredBankAccountJpaEntity.getBankAccountNumber()),
        new me.wane.banking.domain.RegisteredBankAccount.LinkedStatusIsValid(registeredBankAccountJpaEntity.isLinkedStatusIsValid()),
        new me.wane.banking.domain.RegisteredBankAccount.AggregateIdentifier(registeredBankAccountJpaEntity.getAggregateIdentifier())
    );
  }

}
