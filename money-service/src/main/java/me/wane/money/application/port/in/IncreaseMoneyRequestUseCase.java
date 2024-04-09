package me.wane.money.application.port.in;

import me.wane.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyRequestUseCase {

  MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
