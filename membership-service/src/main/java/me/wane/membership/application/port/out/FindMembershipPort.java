package me.wane.membership.application.port.out;


import me.wane.membership.adapter.out.persistence.MembershipJpaEntity;
import me.wane.membership.domain.Membership;

public interface FindMembershipPort {

  MembershipJpaEntity findMembership(Membership.MembershipId membershipId);

}
