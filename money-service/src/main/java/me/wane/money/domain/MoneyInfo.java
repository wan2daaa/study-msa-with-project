package me.wane.money.domain;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MoneyInfo {

  private final String membershipId;
  private final int balance;

  public static MoneyInfo of(String membershipId, int balance) {
    return new MoneyInfo(membershipId, balance);
  }
}
