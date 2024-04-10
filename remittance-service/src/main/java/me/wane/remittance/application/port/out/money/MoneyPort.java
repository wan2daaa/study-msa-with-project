package me.wane.remittance.application.port.out.money;

import me.wane.remittance.application.port.out.membership.MembershipStatus;

public interface MoneyPort {

  MoneyInfo getMoneyInfo(String membershipId);

  boolean requestMoneyRecharging(String membershipId, int amount);
  boolean requestMoneyIncrease(String membershipId, int amount);
  boolean requestMoneyDecrease(String membershipId, int amount);

}
