package me.wane.money.application.port.in;

public record MembershipIdRequestCommand(
    String membershipId
) {

  public static MembershipIdRequestCommand of(String membershipId) {
    return new MembershipIdRequestCommand(membershipId);
  }

}
