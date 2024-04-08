package me.wane.membership.application.port.out;

import me.wane.membership.adapter.out.persistence.MembershipJpaEntity;
import me.wane.membership.domain.Membership;

public interface ModifyMembershipPort {

  MembershipJpaEntity modifyMembership(
      Membership.MembershipId membershipId,
      Membership.MembershipName membershipName,
      Membership.MembershipEmail membershipEmail,
      Membership.MembershipAddress membershipAddress,
      Membership.MembershipIsValid membershipIsValid,
      Membership.MembershipIsCorp membershipIsCorp
  );

}
