package me.wane.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.in.RegisterBankAccountCommand;
import me.wane.banking.application.port.in.RegisterBankAccountUseCase;
import me.wane.banking.domain.RegisteredBankAccount;
import me.wane.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class RegisterBankAccountController {

  private final RegisterBankAccountUseCase registerBankAccountUseCase;

  @PostMapping("/banking/account/register")
  RegisteredBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest request) {

    RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
        .membershipId(request.getMembershipId())
        .bankName(request.getBankName())
        .bankAccountNumber(request.getBankAccountNumber())
        .isValid(request.isValid())
        .build();

    RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(
        command);

    if (registeredBankAccount == null) {
//      TODO : Error Handling
      return null;
    }
    return registeredBankAccount;
  }

}
