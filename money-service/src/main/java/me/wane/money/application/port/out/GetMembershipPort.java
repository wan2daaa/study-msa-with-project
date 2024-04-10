package me.wane.money.application.port.out;

public interface GetMembershipPort {

  public MembershipStatus getMembership(String membershipId);

}
