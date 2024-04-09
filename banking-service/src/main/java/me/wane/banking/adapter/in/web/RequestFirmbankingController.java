package me.wane.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.in.RequestFirmbankingCommand;
import me.wane.banking.application.port.in.RequestFirmbankingUseCase;
import me.wane.banking.domain.FirmbankingRequest;
import me.wane.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestFirmbankingController {

  private final RequestFirmbankingUseCase requestFirmbankingUseCase;

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
}
