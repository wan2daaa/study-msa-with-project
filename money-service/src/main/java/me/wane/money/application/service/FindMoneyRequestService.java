package me.wane.money.application.service;


import lombok.RequiredArgsConstructor;
import me.wane.common.UseCase;
import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.wane.money.application.port.in.FindMoneyRequestUseCase;
import me.wane.money.application.port.in.MembershipIdRequestCommand;
import me.wane.money.application.port.out.GetMoneyPort;
import me.wane.money.domain.MoneyInfo;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@UseCase
public class FindMoneyRequestService implements FindMoneyRequestUseCase {

  private final GetMoneyPort getMoneyPort;

  @Override
  public MoneyInfo findMoneyAmountByMembershipId(MembershipIdRequestCommand command) {

    // membership entity 에서 해당 회원의 금액을 반환
    MemberMoneyJpaEntity memberMoneyJpaEntity = getMoneyPort.findMemberMoneyByMembershipId(
        command.membershipId());

    return MoneyInfo.of(memberMoneyJpaEntity.getMembershipId() + "",
        memberMoneyJpaEntity.getBalance());

  }
}
