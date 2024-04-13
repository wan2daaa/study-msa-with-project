package me.wane.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.in.*;
import me.wane.banking.domain.FirmbankingRequest;
import me.wane.common.WebAdapter;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

  private final RequestFirmbankingUseCase requestFirmbankingUseCase;
  private final UpdateFirmbankingUseCase updateFirmbankingUseCase;

  @PostMapping(path = "/banking/firmbanking/request")
  FirmbankingRequest requestFirmbanking(@RequestBody RequestFirmbankingRequest request) {
    RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
        .toBankName(request.getToBankName())
        .toBankAccountNumber(request.getToBankAccountNumber())
        .fromBankName(request.getFromBankName())
        .fromBankAccountNumber(request.getFromBankAccountNumber())
        .moneyAmount(request.getMoneyAmount())
        .build();

    return requestFirmbankingUseCase.requestFirmbanking(command);
  }

  @PostMapping(path = "/banking/firmbanking/request-eda")
  void requestFirmbankingByEvent(@RequestBody RequestFirmbankingRequest request) {
    RequestFirmbankingCommand command = RequestFirmbankingCommand.builder()
        .toBankName(request.getToBankName())
        .toBankAccountNumber(request.getToBankAccountNumber())
        .fromBankName(request.getFromBankName())
        .fromBankAccountNumber(request.getFromBankAccountNumber())
        .moneyAmount(request.getMoneyAmount())
        .build();

    requestFirmbankingUseCase.requestFirmbankingByEvent(command);
  }

  @PutMapping(path = "/banking/firmbanking/request-eda")
  void updateFirmbankingByEvent(@RequestBody UpdateFirmbankingRequest request) {
    UpdateFirmbankingCommand command = UpdateFirmbankingCommand.builder()
        .firmbankingAggregateIdentifier(request.getFirmbankingRequestAggregateIdentifier())
        .status(request.getStatus())
        .build();

    updateFirmbankingUseCase.updateFirmbankingByEvent(command);
  }


}
