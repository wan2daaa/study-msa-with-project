package me.wane.membership.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.wane.membership.adapter.out.persistence.MembershipJpaEntity;
import me.wane.membership.adapter.out.persistence.MembershipMapper;
import me.wane.membership.application.port.in.ModifyMembershipCommand;
import me.wane.membership.application.port.in.ModifyMembershipUseCase;
import me.wane.membership.application.port.out.ModifyMembershipPort;
import me.wane.membership.common.UseCase;
import me.wane.membership.domain.Membership;
import me.wane.membership.domain.Membership.MembershipAddress;
import me.wane.membership.domain.Membership.MembershipEmail;
import me.wane.membership.domain.Membership.MembershipId;
import me.wane.membership.domain.Membership.MembershipIsCorp;
import me.wane.membership.domain.Membership.MembershipIsValid;
import me.wane.membership.domain.Membership.MembershipName;

@RequiredArgsConstructor
@Transactional
@UseCase
public class ModifyMembershipService implements ModifyMembershipUseCase {

  private final ModifyMembershipPort modifyMembershipPort;
  private final MembershipMapper membershipMapper;


  @Override
  public Membership modifyMembership(ModifyMembershipCommand command) {
    // command -> DB 와 통신을 하고,
    // DB와 통신한 결과값을 리턴하면 됨

    // 비즈니스 로직은 DB 에 가기 위해서
    // DB 가 외부 시스템
    // DB로 가기 위해서는 port, 와 adapter 가 필요

    MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
        new MembershipId(command.getMembershipId()),
        new MembershipName(command.getName()),
        new MembershipEmail(command.getEmail()),
        new MembershipAddress(command.getAddress()),
        new MembershipIsValid(command.isValid()),
        new MembershipIsCorp(command.isCorp())
    );

    return membershipMapper.mapToDomainEntity(jpaEntity);  }
}
