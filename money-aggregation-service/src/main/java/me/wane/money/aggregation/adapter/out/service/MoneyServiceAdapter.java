package me.wane.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import me.wane.common.CommonHttpClient;
import me.wane.common.ExternalSystemAdapter;
import me.wane.money.aggregation.application.port.out.GetMoneySumPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
public class MoneyServiceAdapter implements GetMoneySumPort {

  private final CommonHttpClient moneyServiceHttpClient;

  @Value("${service.money.url}")
  private String moneyServiceEndpoint;

  public MoneyServiceAdapter(CommonHttpClient commonHttpClient,
      @Value("${service.money.url}") String moneyServiceEndpoint) {
    this.moneyServiceHttpClient = commonHttpClient;
    this.moneyServiceEndpoint = moneyServiceEndpoint;
  }

  @Override
  public List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds) {
    String url = String.join("/", moneyServiceEndpoint, "money/member-money");
    ObjectMapper mapper = new ObjectMapper();

    try {
      FindMemberMoneyRequest request = new FindMemberMoneyRequest(membershipIds);
      String jsonResponse = moneyServiceHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).body();
      List<MemberMoney> memberMoneyList = mapper.readValue(jsonResponse, new TypeReference<>() {
      });
      return memberMoneyList;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}