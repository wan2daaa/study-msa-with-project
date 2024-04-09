package me.wane.banking.application.port.out;

import me.wane.banking.adapter.out.external.bank.BankAccount;
import me.wane.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {

  public BankAccount getBankAccountInfo(GetBankAccountRequest request);
}
