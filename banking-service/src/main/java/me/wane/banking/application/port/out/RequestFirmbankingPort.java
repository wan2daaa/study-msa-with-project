package me.wane.banking.application.port.out;

import me.wane.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.wane.banking.domain.FirmbankingRequest;
import me.wane.banking.domain.FirmbankingRequest.*;

public interface RequestFirmbankingPort {

  FirmbankingRequestJpaEntity createFirmbankingRequest(
      FromBankName fromBankName,
      FromBankAccountNumber fromBankAccountNumber,
      ToBankName toBankName,
      ToBankAccountNumber toBankAccountNumber,
      MoneyAmount moneyAmount,
      FirmbankingStatus firmbankingStatus,
      AggregateIdentifier aggregateIdentifier);

  FirmbankingRequestJpaEntity modifyFirmbankingRequest(
      FirmbankingRequestJpaEntity entity
  );

  FirmbankingRequestJpaEntity getFirmbankingRequest(
      FirmbankingRequest.AggregateIdentifier aggregateIdentifier
  );
}
