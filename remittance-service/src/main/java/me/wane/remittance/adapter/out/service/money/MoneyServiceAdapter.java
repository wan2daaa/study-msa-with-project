package me.wane.remittance.adapter.out.service.money;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import me.wane.common.CommonHttpClient;
import me.wane.common.ExternalSystemAdapter;
import me.wane.remittance.application.port.out.money.MoneyInfo;
import me.wane.remittance.application.port.out.money.MoneyPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements MoneyPort {

  private final CommonHttpClient commonHttpClient;

  @Value("${service.money.url}")
  private String moneyServiceEndpoint;


  @Override
  public MoneyInfo getMoneyInfo(String membershipId) {
    String url = String.join("/", this.moneyServiceEndpoint, "money", membershipId);

    try {
      String jsonResponse = commonHttpClient.sendGetRequest(url).body();
      ObjectMapper mapper = new ObjectMapper();

      return mapper.readValue(jsonResponse, MoneyInfo.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean requestMoneyRecharging(String membershipId, int amount) {
    //TODO. not implemented
    return true;
  }

  @Override
  public boolean requestMoneyIncrease(String membershipId, int amount) {
    String url = String.join("/", this.moneyServiceEndpoint, "money", "increaseAsync");
    ChangeMoney requestBody = ChangeMoney.of(membershipId, amount);

    ObjectMapper mapper = new ObjectMapper();

    try {
      String body = mapper.writeValueAsString(requestBody);
      CompletableFuture<HttpResponse<String>> responseCompletableFuture = commonHttpClient.sendPostRequest(
          url, body);

      return responseCompletableFuture.get().statusCode() == 200;
    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public boolean requestMoneyDecrease(String membershipId, int amount) {

    String url = String.join("/", this.moneyServiceEndpoint, "money", "decrease");
    ChangeMoney requestBody = ChangeMoney.of(membershipId, amount);

    ObjectMapper mapper = new ObjectMapper();

    try {
      String body = mapper.writeValueAsString(requestBody);
      CompletableFuture<HttpResponse<String>> responseCompletableFuture = commonHttpClient.sendPostRequest(
          url, body);

      return responseCompletableFuture.get().statusCode() == 200;
    } catch (JsonProcessingException | InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }

  }
}