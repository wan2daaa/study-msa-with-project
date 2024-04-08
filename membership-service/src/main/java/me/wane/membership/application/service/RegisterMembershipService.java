package me.wane.membership.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.wane.common.UseCase;
import me.wane.membership.adapter.out.persistence.MembershipJpaEntity;
import me.wane.membership.adapter.out.persistence.MembershipMapper;
import me.wane.membership.application.port.in.RegisterMembershipCommand;
import me.wane.membership.application.port.in.RegisterMembershipUseCase;
import me.wane.membership.application.port.out.RegisterMembershipPort;
import me.wane.membership.domain.Membership;
import me.wane.membership.domain.Membership.MembershipAddress;
import me.wane.membership.domain.Membership.MembershipEmail;
import me.wane.membership.domain.Membership.MembershipIsCorp;
import me.wane.membership.domain.Membership.MembershipIsValid;
import me.wane.membership.domain.Membership.MembershipName;

@RequiredArgsConstructor
@Transactional
@UseCase
public class RegisterMembershipService implements RegisterMembershipUseCase {

  private final RegisterMembershipPort registerMembershipPort;
  private final MembershipMapper membershipMapper;

  @Override
  public Membership registerMembership(RegisterMembershipCommand command) {
    // command -> DB 와 통신을 하고,
    // DB와 통신한 결과값을 리턴하면 됨

    // 비즈니스 로직은 DB 에 가기 위해서
    // DB 가 외부 시스템
    // DB로 가기 위해서는 port, 와 adapter 가 필요

    MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
        new MembershipName(command.getName()),
        new MembershipEmail(command.getEmail()),
        new MembershipAddress(command.getAddress()),
        new MembershipIsValid(command.isValid()),
        new MembershipIsCorp(command.isCorp())
    );

    System.out.println("jpaEntity = " + jpaEntity);

    return membershipMapper.mapToDomainEntity(jpaEntity);
  }
}
