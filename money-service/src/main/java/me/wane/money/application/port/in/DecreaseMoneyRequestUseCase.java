package me.wane.money.application.port.in;

import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.wane.money.domain.MoneyChangingRequest;

public interface DecreaseMoneyRequestUseCase {

  MoneyChangingRequest decreaseMoneyRequest(DecreaseMoneyRequestCommand command);

}
