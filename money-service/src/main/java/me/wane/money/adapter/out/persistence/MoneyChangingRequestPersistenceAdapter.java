package me.wane.money.adapter.out.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.wane.common.PersistenceAdapter;
import me.wane.money.application.port.out.*;
import me.wane.money.domain.MemberMoney;
import me.wane.money.domain.MemberMoney.MembershipId;
import me.wane.money.domain.MemberMoney.MoneyAggregateIdentifier;
import me.wane.money.domain.MoneyChangingRequest;

@PersistenceAdapter
@RequiredArgsConstructor
public class MoneyChangingRequestPersistenceAdapter implements IncreaseMoneyPort, GetMoneyPort,
    DecreaseMoneyPort, CreateMemberMoneyPort, GetMemberMemberMoneyPort, GetMemberMoneyPort {

  private final SpringDataMoneyChangingRequestRepository moneyChangingRequestRepository;

  private final SpringDataMemberMoneyRepository memberMoneyRepository;

  @Override
  public MoneyChangingRequestJpaEntity createMoneyChangingRequest(
      MoneyChangingRequest.TargetMembershipId targetMembershipId,
      MoneyChangingRequest.MoneyChangingType moneyChangingType,
      MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
      MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus, MoneyChangingRequest.Uuid uuid) {
    return moneyChangingRequestRepository.save(
        new MoneyChangingRequestJpaEntity(
            targetMembershipId.getTargetMembershipId(),
            moneyChangingType.getMoneyChangingType(),
            changingMoneyAmount.getChangingMoneyAmount(),
            new Timestamp(System.currentTimeMillis()),
            moneyChangingStatus.getChangingMoneyStatus(),
            UUID.randomUUID()
        )
    );
  }

  @Override
  public MemberMoneyJpaEntity decreaseMoney(String memberId, int decreaseMoneyAmount) {
    MemberMoneyJpaEntity memberMoneyJpaEntity = this.findMemberMoneyByMembershipId(memberId);

    memberMoneyJpaEntity.setBalance(memberMoneyJpaEntity.getBalance() - decreaseMoneyAmount);
    return memberMoneyRepository.save(memberMoneyJpaEntity);
  }

  @Override
  public MemberMoneyJpaEntity increaseMoney(MemberMoney.MembershipId memberId, int increaseMoneyAmount) {
    MemberMoneyJpaEntity entity;
    try {
      List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(
          Long.parseLong(memberId.getMembershipId()));
      entity = entityList.get(0);

      entity.setBalance(entity.getBalance() + increaseMoneyAmount);
      return memberMoneyRepository.save(entity);
    } catch (Exception e) {
      entity = new MemberMoneyJpaEntity(
          Long.parseLong(memberId.getMembershipId()),
          increaseMoneyAmount
      );
      entity = memberMoneyRepository.save(entity);
      return entity;
    }

  }

  @Override
  public MemberMoneyJpaEntity findMemberMoneyByMembershipId(String membershipId) {
    return memberMoneyRepository.findByMembershipId(Long.parseLong(membershipId)).get(0);
  }


  @Override
  public void createMemberMoney(MembershipId membershipId, MoneyAggregateIdentifier aggregateIdentifier) {
    memberMoneyRepository.save(
        new MemberMoneyJpaEntity(
            Long.parseLong(membershipId.getMembershipId()),
            0,
            aggregateIdentifier.getAggregateIdentifier()
        )
    );
  }

  @Override
  public MemberMoneyJpaEntity getMemberMoney(MembershipId membershipId) {
    List<MemberMoneyJpaEntity> entityList = memberMoneyRepository.findByMembershipId(
        Long.parseLong(membershipId.getMembershipId()));

    if (entityList.isEmpty()) {
      return memberMoneyRepository.save(
          new MemberMoneyJpaEntity(
              Long.parseLong(membershipId.getMembershipId()),
              0,
              ""
          )
      );
    }

    return entityList.get(0);
  }

  @Override
  public List<MemberMoneyJpaEntity> getMoneyListByMembershipIds(List<String> membershipIds) {
    return memberMoneyRepository.findByMembershipIds(convertMembershipIds(membershipIds));
  }

  private List<Long> convertMembershipIds(List<String> membershipIds) {
    return membershipIds.stream().map(Long::parseLong).toList();
  }
}