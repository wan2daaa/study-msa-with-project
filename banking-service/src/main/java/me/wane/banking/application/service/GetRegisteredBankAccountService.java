package me.wane.banking.application.service;

import lombok.RequiredArgsConstructor;
import me.wane.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import me.wane.banking.application.port.in.GetRegisteredBankAccountCommand;
import me.wane.banking.application.port.in.GetRegisteredBankAccountUseCase;
import me.wane.banking.application.port.out.GetRegisteredBankAccountPort;
import me.wane.banking.domain.RegisteredBankAccount;
import me.wane.common.UseCase;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class GetRegisteredBankAccountService implements GetRegisteredBankAccountUseCase {

  private final RegisteredBankAccountMapper mapper;
  private final CommandGateway commandGateway;
  private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;

  @Override
  public RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
    return mapper.mapToDomainEntity(getRegisteredBankAccountPort.getRegisteredBankAccount(command));
  }
}
