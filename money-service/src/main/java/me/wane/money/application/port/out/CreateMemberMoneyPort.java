package me.wane.money.application.port.out;

import me.wane.money.domain.MemberMoney;

public interface CreateMemberMoneyPort {

  void createMemberMoney(
      MemberMoney.MembershipId membershipId,
      MemberMoney.MoneyAggregateIdentifier aggregateIdentifier

  );

}
