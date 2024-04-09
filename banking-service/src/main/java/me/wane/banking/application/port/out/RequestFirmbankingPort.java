package me.wane.banking.application.port.out;

import me.wane.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.wane.banking.domain.FirmbankingRequest;

public interface RequestFirmbankingPort {

  FirmbankingRequestJpaEntity createFirmbankingRequest(
      FirmbankingRequest.FromBankName fromBankName,
      FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber,
      FirmbankingRequest.ToBankName toBankName,
      FirmbankingRequest.ToBankAccountNumber toBankAccountNumber,
      FirmbankingRequest.MoneyAmount moneyAmount,
      FirmbankingRequest.FirmbankingStatus firmbankingStatus
  );

  FirmbankingRequestJpaEntity modifyFirmbankingRequest(
      FirmbankingRequestJpaEntity entity
  );
}
