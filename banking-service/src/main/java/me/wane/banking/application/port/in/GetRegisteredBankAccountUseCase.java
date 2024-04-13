package me.wane.banking.application.port.in;

import me.wane.banking.domain.RegisteredBankAccount;

public interface GetRegisteredBankAccountUseCase {

  RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand membershipId);


}
