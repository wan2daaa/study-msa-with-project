package me.wane.money.adapter.out.persistence;

import me.wane.money.domain.MemberMoney;
import me.wane.money.domain.MemberMoney.MembershipId;
import me.wane.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberMoneyMapper {
  public MemberMoney mapToDomainEntity(MemberMoneyJpaEntity moneyChangingRequestJpaEntity) {
    return MemberMoney.generateMemberMoney(
        new MemberMoney.MemberMoneyId(moneyChangingRequestJpaEntity.getMembershipId() + ""),
        new MembershipId(moneyChangingRequestJpaEntity.getMembershipId() + ""),
        new MemberMoney.MoneyBalance(moneyChangingRequestJpaEntity.getBalance())
    );
  }
}
