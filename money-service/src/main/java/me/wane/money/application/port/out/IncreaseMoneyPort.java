package me.wane.money.application.port.out;

import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.wane.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import me.wane.money.domain.MemberMoney;
import me.wane.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {

  MoneyChangingRequestJpaEntity createMoneyChangingRequest(
      MoneyChangingRequest.TargetMembershipId targetMembershipId,
      MoneyChangingRequest.MoneyChangingType moneyChangingType,
      MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
      MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
      MoneyChangingRequest.Uuid uuid
  );

  MemberMoneyJpaEntity increaseMoney(
      MemberMoney.MembershipId memberId,
      int increaseMoneyAmount
  );
}
