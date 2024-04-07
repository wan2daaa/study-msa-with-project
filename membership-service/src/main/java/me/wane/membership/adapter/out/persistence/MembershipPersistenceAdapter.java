package me.wane.membership.adapter.out.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.wane.membership.application.port.out.FindMembershipPort;
import me.wane.membership.application.port.out.RegisterMembershipPort;
import me.wane.membership.common.PersistenceAdapter;
import me.wane.membership.domain.Membership;
import me.wane.membership.domain.Membership.MembershipAddress;
import me.wane.membership.domain.Membership.MembershipEmail;
import me.wane.membership.domain.Membership.MembershipId;
import me.wane.membership.domain.Membership.MembershipIsCorp;
import me.wane.membership.domain.Membership.MembershipIsValid;
import me.wane.membership.domain.Membership.MembershipName;

@RequiredArgsConstructor
@PersistenceAdapter
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort {

  private final SpringDataMembershipRepository membershipRepository;

  @Override
  public MembershipJpaEntity createMembership(
      MembershipName membershipName,
      MembershipEmail membershipEmail,
      MembershipAddress membershipAddress,
      MembershipIsValid membershipIsValid,
      MembershipIsCorp membershipIsCorp
  ) {
    return membershipRepository.save(
        new MembershipJpaEntity(
            membershipName.getNameValue(),
            membershipEmail.getEmailValue(),
            membershipAddress.getAddressValue(),
            membershipIsValid.isValidValue(),
            membershipIsCorp.isCorpValue()
        )
    );
  }

  @Override
  public MembershipJpaEntity findMembership(MembershipId membershipId) {
    return membershipRepository.getById(
        Long.parseLong(membershipId.getMembershipId()));
  }
}
