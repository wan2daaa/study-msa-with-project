package me.wane.money.application.port.out;

import java.util.List;
import me.wane.money.adapter.out.persistence.MemberMoneyJpaEntity;
import me.wane.money.domain.MemberMoney;

public interface GetMemberMoneyPort {

  List<MemberMoneyJpaEntity> getMoneyListByMembershipIds(List<String> membershipIds);

}
