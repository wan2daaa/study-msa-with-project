package me.wane.payment.application.port.out;

public interface GetMembershipPort {
    public MembershipStatus getMembership(String membershipId);
}
