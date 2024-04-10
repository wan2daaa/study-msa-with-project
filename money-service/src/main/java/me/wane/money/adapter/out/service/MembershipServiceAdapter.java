package me.wane.money.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.wane.common.CommonHttpClient;
import me.wane.money.application.port.out.GetMembershipPort;
import me.wane.money.application.port.out.MembershipStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {

  private final CommonHttpClient commonHttpClient;
  private final String membershipUrl;

  public MembershipServiceAdapter(
      CommonHttpClient commonHttpClient,
      @Value("${service.membership.url}") String membershipUrl
  ) {
    this.commonHttpClient = commonHttpClient;
    this.membershipUrl = membershipUrl;
  }

  @Override
  public MembershipStatus getMembership(String membershipId) {
    // 실제로 http Call을 통해서 Membership을 호출
    // http Client 구현

    String url = String.join("/", membershipUrl, "membership", membershipId);

    try {
          String stringResponse = commonHttpClient.sendGetRequest(url).body();

          ObjectMapper objectMapper = new ObjectMapper();

          Membership membership = objectMapper.readValue(stringResponse, Membership.class);

          if (membership.isValid()) {
            return new MembershipStatus(membership.getMembershipId(), true);
      } else {
        return new MembershipStatus(membership.getMembershipId(), false);
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
