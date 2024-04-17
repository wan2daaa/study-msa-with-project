package me.wane.money.aggregation.application.port.in;

public interface GetMoneySumByAddressUseCase {
  int getMoneySumByAddress (GetMoneySumByAddressCommand command);
}