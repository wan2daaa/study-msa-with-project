package me.wane.banking.adapter.out.external.bank;

import lombok.RequiredArgsConstructor;
import me.wane.banking.adapter.out.persistence.SpringDataRegisteredBankAccountRepository;
import me.wane.banking.application.port.out.RequestBankAccountInfoPort;
import me.wane.banking.application.port.out.RequestExternalFirmbankingPort;
import me.wane.common.ExternalSystemAdapter;

@RequiredArgsConstructor
@ExternalSystemAdapter
public class BankAccountAdapter implements RequestBankAccountInfoPort,
    RequestExternalFirmbankingPort {

  private final SpringDataRegisteredBankAccountRepository registeredBankAccountRepository;

  @Override
  public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

    // 실제로 외부 은행에서 http를 통해서
    // 실제 은행 계좌 정보를 가져오고

    // 실제 은행계좌 -> account

    return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
  }

  @Override
  public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request) {

    // 실제로 외부 은행에 http 통신을 통해서
    // 펌뱅킹 요청을 하고

    // 그 결과를
    // 외부 은행의 실제 결과를 -> 페이의 FirmbankingResult 로 파싱
    return new FirmbankingResult(0);

  }
}
