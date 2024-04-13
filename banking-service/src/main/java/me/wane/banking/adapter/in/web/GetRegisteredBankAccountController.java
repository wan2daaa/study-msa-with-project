package me.wane.banking.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.banking.application.port.in.GetRegisteredBankAccountCommand;
import me.wane.banking.application.port.in.GetRegisteredBankAccountUseCase;
import me.wane.banking.domain.RegisteredBankAccount;
import me.wane.common.WebAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class GetRegisteredBankAccountController {

  private final GetRegisteredBankAccountUseCase getRegisteredBankAccountUseCase;

  @GetMapping("/banking/account/{membershipId}")
  RegisteredBankAccount getRegisteredBankAccount(@PathVariable String membershipId) {
    GetRegisteredBankAccountCommand command = GetRegisteredBankAccountCommand.builder()
        .membershipId(membershipId).build();

    return getRegisteredBankAccountUseCase.getRegisteredBankAccount(command);
  }

}
