package me.wane.money.application.port.out;

import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;

public interface GetMoneyPort {

  MemberMoneyJpaEntity findMemberMoneyByMembershipId(String membershipId);

}
