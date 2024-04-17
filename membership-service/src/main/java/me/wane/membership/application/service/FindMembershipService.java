package me.wane.membership.application.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.common.UseCase;
import me.wane.membership.adapter.out.persistence.MembershipJpaEntity;
import me.wane.membership.adapter.out.persistence.MembershipMapper;
import me.wane.membership.application.port.in.FindMembershipCommand;
import me.wane.membership.application.port.in.FindMembershipListByAddressCommand;
import me.wane.membership.application.port.in.FindMembershipUseCase;
import me.wane.membership.application.port.out.FindMembershipPort;
import me.wane.membership.domain.Membership;
import me.wane.membership.domain.Membership.MembershipAddress;
import me.wane.membership.domain.Membership.MembershipId;

@RequiredArgsConstructor
@UseCase
@Transactional
public class FindMembershipService implements FindMembershipUseCase {

  private final FindMembershipPort findMembershipPort;
  private final MembershipMapper membershipMapper;

  @Override
  public Membership findMembership(FindMembershipCommand command) {
    MembershipJpaEntity entity = findMembershipPort.findMembership(
        new MembershipId(command.getMembershipId()));
    return membershipMapper.mapToDomainEntity(entity);
  }

  @Override
  public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {

    List<MembershipJpaEntity> entityList = findMembershipPort.findMembershipListByAddress(
        new MembershipAddress(command.getAddress())
    );

    return entityList.stream().map(membershipMapper::mapToDomainEntity).toList();
  }
}
