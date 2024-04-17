package me.wane.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;
import me.wane.common.CommonHttpClient;
import me.wane.common.ExternalSystemAdapter;
import me.wane.money.aggregation.application.port.out.GetMembershipPort;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
public class MembershipServiceAdapter implements GetMembershipPort {

  private final CommonHttpClient commonHttpClient;
  private final String membershipServiceUrl;

  public MembershipServiceAdapter(
      CommonHttpClient commonHttpClient,
      @Value("${service.membership.url}") String membershipServiceUrl) {
    this.commonHttpClient = commonHttpClient;
    this.membershipServiceUrl = membershipServiceUrl;
  }

  @Override
  public List<String> getMembershipByAddress(String address) {
    String url = String.join("/", membershipServiceUrl, "membership/address", address);
    try {
      String jsonResponse = commonHttpClient.sendGetRequest(url).body();

      ObjectMapper mapper = new ObjectMapper();
      List<Membership> membershipList = mapper.readValue(jsonResponse, new TypeReference<>() {
      });
      List<String> membershipIds = membershipList.stream()
          .map(Membership::getMembershipId)
          .collect(Collectors.toList());

      return membershipIds;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}