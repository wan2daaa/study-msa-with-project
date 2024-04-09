package me.wane.banking.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.wane.banking.adapter.out.external.bank.BankAccount;
import me.wane.banking.adapter.out.external.bank.GetBankAccountRequest;
import me.wane.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import me.wane.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import me.wane.banking.application.port.in.RegisterBankAccountCommand;
import me.wane.banking.application.port.in.RegisterBankAccountUseCase;
import me.wane.banking.application.port.out.RegisterBankAccountPort;
import me.wane.banking.application.port.out.RequestBankAccountInfoPort;
import me.wane.banking.domain.RegisteredBankAccount;
import me.wane.banking.domain.RegisteredBankAccount.BankAccountNumber;
import me.wane.banking.domain.RegisteredBankAccount.BankName;
import me.wane.banking.domain.RegisteredBankAccount.LinkedStatusIsValid;
import me.wane.banking.domain.RegisteredBankAccount.MembershipId;
import me.wane.common.UseCase;

@RequiredArgsConstructor
@Transactional
@UseCase
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

  private final RegisterBankAccountPort registerBankAccountPort;
  private final RegisteredBankAccountMapper registeredBankAccountMapper;

  private final RequestBankAccountInfoPort requestBankAccountInfoPort;

  @Override
  public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

    //은행 계좌를 등록해야 하는 서비스 (비즈니스 로직)
//    command.getMembershipId(); //실제로 존재하는지 안하는지 확인 하는 과정 필요

    // (멤버 서비스도 확인?) 여기서는 skip

    // 1. 외부 실제 은행에 등록된 계좌인지(정상인지) 확인한다.
    // 외부의 은행에 이 계좌가 정상인지? 확인
    // 비즈니스 로직 -> External System
    // Port -> Adapter -> External System

    // 실제 외부의 은행 계좌 정보를 가져온다
    BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(
        new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));

    boolean accountIsValid = accountInfo.isValid();

// 2. 등록가능한 계좌라면, 등록한다. 성공하면, 등록에 성공한 등록 정보를 리턴
    // 2-1. 등록가능하지 않은 계좌라면, 에러를 리턴

    if (accountIsValid) {
      //등록 정보 저장
      RegisteredBankAccountJpaEntity jpaEntity = registerBankAccountPort.createRegisteredBankAccount(
          new MembershipId(command.getMembershipId()),
          new BankName(command.getBankName()),
          new BankAccountNumber(command.getBankAccountNumber()),
          new LinkedStatusIsValid(command.isValid())
      );

      return registeredBankAccountMapper.mapToDomainEntity(jpaEntity);
    } else {
      return null;
    }

  }




}
