package me.wane.banking.application.port.out;

import me.wane.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.wane.banking.application.port.in.GetRegisteredBankAccountCommand;

public interface GetRegisteredBankAccountPort {


  RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}
