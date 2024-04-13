package me.wane.banking.application.port.in;

import me.wane.banking.domain.RegisteredBankAccount;
import me.wane.common.UseCase;

@UseCase
public interface RegisterBankAccountUseCase {

  //command 란 어떤 명령 바깥에서 안으로 들어오는데 있어서 이 request를 그대로 주는게 아니라 중간에서 command 라는 개념을 사용해서 request 를 command 로 바꿈
  RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);

  void registerBankAccountByEvent(RegisterBankAccountCommand command);
}
