package me.wane.banking.application.service;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.wane.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.wane.banking.adapter.out.external.bank.FirmbankingResult;
import me.wane.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.wane.banking.adapter.out.persistence.FirmbankingRequestMapper;
import me.wane.banking.application.port.in.RequestFirmbankingCommand;
import me.wane.banking.application.port.in.RequestFirmbankingUseCase;
import me.wane.banking.application.port.out.RequestExternalFirmbankingPort;
import me.wane.banking.application.port.out.RequestFirmbankingPort;
import me.wane.banking.domain.FirmbankingRequest;
import me.wane.banking.domain.FirmbankingRequest.FirmbankingStatus;
import me.wane.banking.domain.FirmbankingRequest.FromBankAccountNumber;
import me.wane.banking.domain.FirmbankingRequest.FromBankName;
import me.wane.banking.domain.FirmbankingRequest.MoneyAmount;
import me.wane.banking.domain.FirmbankingRequest.ToBankAccountNumber;
import me.wane.banking.domain.FirmbankingRequest.ToBankName;
import me.wane.common.UseCase;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase {

  private final FirmbankingRequestMapper mapper;
  private final RequestFirmbankingPort requestFirmbankingPort;

  private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;

  @Override
  public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {

    // 비즈니스 로직
    // a 계좌에서 b 계좌로 돈을 보내는 것

    // 1. 요청에 대해 정보를 먼저 작성,  "요청" 상태로
    FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
        new FromBankName(command.getFromBankName()),
        new FromBankAccountNumber(command.getFromBankAccountNumber()),
        new ToBankName(command.getToBankName()),
        new ToBankAccountNumber(command.getToBankAccountNumber()),
        new MoneyAmount(command.getMoneyAmount()),
        new FirmbankingStatus(0)
    );

    // 2. 외부 은행에 펌뱅킹 요청
    FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(
        new ExternalFirmbankingRequest(
            command.getFromBankName(),
            command.getFromBankAccountNumber(),
            command.getToBankName(),
            command.getToBankAccountNumber()
        )
    );

    //Transactional UUID
    UUID uuid = UUID.randomUUID();
    requestedEntity.setUuid(uuid);

    // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 update
    if (result.getResultCode() == 0) {
      // 성공
      requestedEntity.setFirmbankingStatus(1);
    } else {
      requestedEntity.setFirmbankingStatus(2);
    }

    // 4. 결과를 리턴하기 전에 바뀐 상태 값읋 기준으로 다시 save
    return mapper.mapToDomainEntity(
        requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), uuid
    );
  }
}
