package me.wane.remittance.adapter.out.service.banking;

import lombok.RequiredArgsConstructor;
import me.wane.common.CommonHttpClient;
import me.wane.common.ExternalSystemAdapter;
import me.wane.remittance.application.port.out.banking.BankingInfo;
import me.wane.remittance.application.port.out.banking.BankingPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

  private final CommonHttpClient commonHttpClient;

  @Value("${service.banking.url}")
  private String bankingServiceEndpoint;


  @Override
  public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber) {
    // TODO. not yet implemented
    return new BankingInfo(bankName, bankAccountNumber, true);
  }

  @Override
  public boolean requestFirmbanking(String bankName, String bankAccountNumber, int amount) {
    // TODO. not yet implemented
    return true;
  }
}