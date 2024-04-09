package me.wane.banking.adapter.out.persistence;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.out.RegisterBankAccountPort;
import me.wane.banking.application.port.out.RequestFirmbankingPort;
import me.wane.banking.domain.FirmbankingRequest.FirmbankingStatus;
import me.wane.banking.domain.FirmbankingRequest.FromBankAccountNumber;
import me.wane.banking.domain.FirmbankingRequest.FromBankName;
import me.wane.banking.domain.FirmbankingRequest.MoneyAmount;
import me.wane.banking.domain.FirmbankingRequest.ToBankAccountNumber;
import me.wane.banking.domain.FirmbankingRequest.ToBankName;
import me.wane.banking.domain.RegisteredBankAccount.BankAccountNumber;
import me.wane.banking.domain.RegisteredBankAccount.BankName;
import me.wane.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import me.wane.banking.domain.RegisteredBankAccount.MembershipId;
import me.wane.common.PersistenceAdapter;

@RequiredArgsConstructor
@PersistenceAdapter
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

  private final SpringDataFirmbankingRequestRepository firmbankingRequestRepository;


  @Override
  public FirmbankingRequestJpaEntity createFirmbankingRequest(FromBankName fromBankName,
      FromBankAccountNumber fromBankAccountNumber, ToBankName toBankName,
      ToBankAccountNumber toBankAccountNumber, MoneyAmount moneyAmount,
      FirmbankingStatus firmbankingStatus) {
    FirmbankingRequestJpaEntity entity = firmbankingRequestRepository.save(
        new FirmbankingRequestJpaEntity(
            fromBankName.getFromBankName(),
            fromBankAccountNumber.getFromBankAccountNumber(),
            toBankName.getToBankName(),
            toBankAccountNumber.getToBankAccountNumber(),
            moneyAmount.getMoneyAmount(),
            firmbankingStatus.getFirmBankingStatus(),
            UUID.randomUUID()
        ));

    return entity;
  }

  @Override
  public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
    return firmbankingRequestRepository.save(entity);
  }
}
