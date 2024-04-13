package me.wane.banking.application.service;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import me.wane.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import me.wane.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import me.wane.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.wane.banking.adapter.out.external.bank.FirmbankingResult;
import me.wane.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import me.wane.banking.adapter.out.persistence.FirmbankingRequestMapper;
import me.wane.banking.application.port.in.*;
import me.wane.banking.application.port.out.RequestExternalFirmbankingPort;
import me.wane.banking.application.port.out.RequestFirmbankingPort;
import me.wane.banking.domain.FirmbankingRequest;
import me.wane.banking.domain.FirmbankingRequest.*;
import me.wane.common.UseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestFirmbankingService implements RequestFirmbankingUseCase, UpdateFirmbankingUseCase {

  private final FirmbankingRequestMapper mapper;
  private final RequestFirmbankingPort requestFirmbankingPort;

  private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;
  private final CommandGateway gateway;

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
        new FirmbankingStatus(0),
        new AggregateIdentifier("")
    );

    // 2. 외부 은행에 펌뱅킹 요청
    FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(
        new ExternalFirmbankingRequest(
            command.getFromBankName(),
            command.getFromBankAccountNumber(),
            command.getToBankName(),
            command.getToBankAccountNumber(),
            command.getMoneyAmount()
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

  @Override
  public void requestFirmbankingByEvent(RequestFirmbankingCommand command) {
    CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
        .fromBankName(command.getFromBankName())
        .fromBankAccountNumber(command.getFromBankAccountNumber())
        .toBankName(command.getToBankName())
        .toBankAccountNumber(command.getToBankAccountNumber())
        .moneyAmount(command.getMoneyAmount())
        .build();
    // Command -> Event Sourcing
    gateway.send(createFirmbankingRequestCommand)
        .whenComplete((result, throwable) -> {
          if (throwable != null) {
            System.out.println("throwable = " + throwable);
            throw new RuntimeException(throwable);
          } else {
            // 성공
            // Request Firmbanking 의 DB save
            System.out.println("result = " + result);

            FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FromBankName(command.getFromBankName()),
                new FromBankAccountNumber(command.getFromBankAccountNumber()),
                new ToBankName(command.getToBankName()),
                new ToBankAccountNumber(command.getToBankAccountNumber()),
                new MoneyAmount(command.getMoneyAmount()),
                new FirmbankingStatus(0),
                new AggregateIdentifier(result.toString())
            );

            FirmbankingResult firmbankingResult = requestExternalFirmbankingPort.requestExternalFirmbanking(
                new ExternalFirmbankingRequest(
                    command.getFromBankName(),
                    command.getFromBankAccountNumber(),
                    command.getToBankName(),
                    command.getToBankAccountNumber(),
                    command.getMoneyAmount()
                )
            );
            if (firmbankingResult.getResultCode() == 0) {
              // 성공
              requestedEntity.setFirmbankingStatus(1);
            } else {
              requestedEntity.setFirmbankingStatus(2);
            }

            requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity);
          }
        });



  }

  @Override
  public void updateFirmbankingByEvent(UpdateFirmbankingCommand command) {
    UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand =
        new UpdateFirmbankingRequestCommand(command.getFirmbankingAggregateIdentifier(), command.getStatus());

    gateway.send(updateFirmbankingRequestCommand)
        .whenComplete((result, throwable) -> {

          if (throwable != null) {
            System.out.println("throwable = " + throwable);
            throw new RuntimeException(throwable);
          } else {
            System.out.println("result = " + result.toString());
            FirmbankingRequestJpaEntity entity = requestFirmbankingPort.getFirmbankingRequest(
                new AggregateIdentifier(command.getFirmbankingAggregateIdentifier()));

            entity.setFirmbankingStatus(command.getStatus());
            requestFirmbankingPort.modifyFirmbankingRequest(entity);
          }
        });
  }
}
