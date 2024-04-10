package me.wane.money.application.port.in;

import me.wane.money.domain.MoneyInfo;

public interface FindMoneyRequestUseCase {

  MoneyInfo findMoneyAmountByMembershipId(MembershipIdRequestCommand command);

}
