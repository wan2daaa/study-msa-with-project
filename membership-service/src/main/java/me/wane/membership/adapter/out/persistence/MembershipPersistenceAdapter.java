package me.wane.membership.adapter.out.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.common.PersistenceAdapter;
import me.wane.membership.application.port.out.FindMembershipPort;
import me.wane.membership.application.port.out.ModifyMembershipPort;
import me.wane.membership.application.port.out.RegisterMembershipPort;
import me.wane.membership.domain.Membership.MembershipAddress;
import me.wane.membership.domain.Membership.MembershipEmail;
import me.wane.membership.domain.Membership.MembershipId;
import me.wane.membership.domain.Membership.MembershipIsCorp;
import me.wane.membership.domain.Membership.MembershipIsValid;
import me.wane.membership.domain.Membership.MembershipName;

@RequiredArgsConstructor
@PersistenceAdapter
public class MembershipPersistenceAdapter implements RegisterMembershipPort, FindMembershipPort,
    ModifyMembershipPort {

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
            membershipAddress.getAddressValue(),
            membershipEmail.getEmailValue(),
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

  @Override
  public List<MembershipJpaEntity> findMembershipListByAddress(MembershipAddress membershipAddress) {
    // 관악구, 서초구, 강남구 중 하나
    String address = membershipAddress.getAddressValue();

    return membershipRepository.findAllByAddress(address);
  }

  @Override
  public MembershipJpaEntity modifyMembership(MembershipId membershipId,
      MembershipName membershipName, MembershipEmail membershipEmail,
      MembershipAddress membershipAddress, MembershipIsValid membershipIsValid,
      MembershipIsCorp membershipIsCorp) {
    MembershipJpaEntity entity = membershipRepository.getById(
        Long.parseLong(membershipId.getMembershipId()));

    entity.setName(membershipName.getNameValue());
    entity.setEmail(membershipEmail.getEmailValue());
    entity.setAddress(membershipAddress.getAddressValue());
    entity.setValid(membershipIsValid.isValidValue());
    entity.setCorp(membershipIsCorp.isCorpValue());

    return membershipRepository.save(entity);
  }
}
