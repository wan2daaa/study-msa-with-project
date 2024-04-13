package me.wane.money.application.port.out;

import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.wane.money.domain.MemberMoney.MembershipId;

public interface GetMemberMemberMoneyPort {

  MemberMoneyJpaEntity getMemberMoney(
      MembershipId membershipId
  );

}
