package me.wane.membership.adapter.out.persistence;

import me.wane.membership.domain.Membership;
import me.wane.membership.domain.Membership.MembershipEmail;
import me.wane.membership.domain.Membership.MembershipId;
import me.wane.membership.domain.Membership.MembershipName;
import org.springframework.stereotype.Component;

@Component
public class MembershipMapper {

  public Membership mapToDomainEntity(MembershipJpaEntity membershipJpaEntity) {
    return Membership.generateMember(
        new MembershipId(membershipJpaEntity.getMembershipId() + ""),
        new MembershipName(membershipJpaEntity.getName()),
        new MembershipEmail(membershipJpaEntity.getEmail()),
        new Membership.MembershipAddress(membershipJpaEntity.getAddress()),
        new Membership.MembershipIsValid(membershipJpaEntity.isValid()),
        new Membership.MembershipIsCorp(membershipJpaEntity.isCorp())
    );
  }

}
