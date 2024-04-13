package me.wane.banking.adapter.out.persistence;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.out.RequestFirmbankingPort;
import me.wane.banking.domain.FirmbankingRequest.*;
import me.wane.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

  private final SpringDataFirmbankingRequestRepository firmbankingRequestRepository;


  @Override
  public FirmbankingRequestJpaEntity createFirmbankingRequest(FromBankName fromBankName,
      FromBankAccountNumber fromBankAccountNumber, ToBankName toBankName,
      ToBankAccountNumber toBankAccountNumber, MoneyAmount moneyAmount,
      FirmbankingStatus firmbankingStatus, AggregateIdentifier aggregateIdentifier) {
    FirmbankingRequestJpaEntity entity = firmbankingRequestRepository.save(
        new FirmbankingRequestJpaEntity(
            fromBankName.getFromBankName(),
            fromBankAccountNumber.getFromBankAccountNumber(),
            toBankName.getToBankName(),
            toBankAccountNumber.getToBankAccountNumber(),
            moneyAmount.getMoneyAmount(),
            firmbankingStatus.getFirmBankingStatus(),
            UUID.randomUUID(),
            aggregateIdentifier.getAggregateIdentifier()
        ));

    return entity;
  }

  @Override
  public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
    return firmbankingRequestRepository.save(entity);
  }

  @Override
  public FirmbankingRequestJpaEntity getFirmbankingRequest(AggregateIdentifier aggregateIdentifier) {
    List<FirmbankingRequestJpaEntity> entityList = firmbankingRequestRepository.findByAggregateIdentifier(
        aggregateIdentifier.getAggregateIdentifier());
    if (entityList.isEmpty()) {
      return null;
    }

    return entityList.get(0);
  }
}
