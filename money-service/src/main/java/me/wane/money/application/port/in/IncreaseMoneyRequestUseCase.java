package me.wane.money.application.port.in;

import java.util.List;
import me.wane.money.adapter.in.web.FindMemberMoneyListByMembershipIdsCommand;
import me.wane.money.domain.MemberMoney;
import me.wane.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {

  MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);

  MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command);

  void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command);

  List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command);
}
