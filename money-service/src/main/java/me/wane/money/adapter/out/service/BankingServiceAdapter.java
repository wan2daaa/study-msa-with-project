package me.wane.money.adapter.out.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.wane.common.CommonHttpClient;
import me.wane.money.application.port.out.GetRegisteredBankAccountPort;
import me.wane.money.application.port.out.RegisteredBankAccountAggregateIdentifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component

public class BankingServiceAdapter implements GetRegisteredBankAccountPort {

  private final CommonHttpClient commonHttpClient;
  private final String bankingUrl;

  public BankingServiceAdapter(
      CommonHttpClient commonHttpClient,
      @Value("${service.banking.url}") String bankingUrl
  ) {
    this.commonHttpClient = commonHttpClient;
    this.bankingUrl = bankingUrl;
  }


  @Override
  public RegisteredBankAccountAggregateIdentifier getRegisteredBankAccount(String membershipId) {

    String url = String.join("/", bankingUrl, "banking/account", membershipId);

    try {
      String jsonResponse = commonHttpClient.sendGetRequest(url).body();

      ObjectMapper mapper = new ObjectMapper();
      RegisteredBankAccount registeredBankAccount = mapper.readValue(jsonResponse, RegisteredBankAccount.class);

      return new RegisteredBankAccountAggregateIdentifier(
          registeredBankAccount.getRegisteredBankAccountId(),
          registeredBankAccount.getAggregateIdentifier(),
          registeredBankAccount.getMembershipId(),
          registeredBankAccount.getBankName(),
          registeredBankAccount.getBankAccountNumber()
      );
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
