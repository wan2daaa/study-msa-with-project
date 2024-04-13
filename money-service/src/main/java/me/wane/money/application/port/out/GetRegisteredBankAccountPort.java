package me.wane.money.application.port.out;

public interface GetRegisteredBankAccountPort {

  RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId);
}
