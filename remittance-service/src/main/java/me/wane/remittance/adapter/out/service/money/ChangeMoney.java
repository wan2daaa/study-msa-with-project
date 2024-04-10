package me.wane.remittance.adapter.out.service.money;

public record ChangeMoney (
  String membershipId,
  int amount
){

  public static ChangeMoney of(String membershipId, int amount) {
    return new ChangeMoney(membershipId, amount);
  }
}
