package me.wane.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.common.WebAdapter;
import me.wane.money.application.port.in.IncreaseMoneyRequestCommand;
import me.wane.money.application.port.in.IncreaseMoneyRequestUseCase;
import me.wane.money.domain.MoneyChangingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class RequestMoneyChangingController {

  private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

  @PostMapping(path = "/money/increase")
  MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
    IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
        .targetMembershipId(request.getTargetMembershipId())
        .amount(request.getAmount())
        .build();

    MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

    // MoneyChangingRequest -> MoneyChangingResultDetail
    MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
        moneyChangingRequest.getMoneyChangingRequestId(),
        0,
        0,
        moneyChangingRequest.getChangingMoneyAmount());
    return resultDetail;
  }

  @PostMapping(path = "/money/decrease")
  MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request) {
//        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
//                .membershipId(request.getMembershipId())
//                .bankName(request.getBankName())
//                .bankAccountNumber(request.getBankAccountNumber())
//                .isValid(request.isValid())
//                .build();

    // registeredBankAccountUseCase.registerBankAccount(command)
    // -> MoneyChangingResultDetail
    // return decreaseMoneyRequestUseCase.decreaseMoneyChangingRequest(command);
    return null;
  }

}
