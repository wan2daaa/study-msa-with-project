package me.wane.remittance.application.service;

import me.wane.common.UseCase;
import me.wane.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import me.wane.remittance.adapter.out.persistence.RemittanceRequestMapper;
import me.wane.remittance.application.port.in.RequestRemittanceCommand;
import me.wane.remittance.application.port.in.RequestRemittanceUseCase;
import me.wane.remittance.application.port.out.RequestRemittancePort;
import me.wane.remittance.application.port.out.banking.BankingPort;
import me.wane.remittance.application.port.out.membership.MembershipPort;
import me.wane.remittance.application.port.out.membership.MembershipStatus;
import me.wane.remittance.application.port.out.money.MoneyInfo;
import me.wane.remittance.application.port.out.money.MoneyPort;
import me.wane.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
@Transactional
public class RequestRemittanceService implements RequestRemittanceUseCase {

  private final RequestRemittancePort requestRemittancePort;
  private final RemittanceRequestMapper mapper;
  private final MembershipPort membershipPort;
  private final MoneyPort moneyPort;
  private final BankingPort bankingPort;


  @Override
  public RemittanceRequest requestRemittance(RequestRemittanceCommand command) {

    // 0. 송금 요청 상태를 시작 상태로 기록 (persistence layer)
    RemittanceRequestJpaEntity entity = requestRemittancePort.createRemittanceRequestHistory(
        command);

    // 1. from 멤버십 상태 확인 (membership service)
    MembershipStatus membershipStatus = membershipPort.getMembershipStatus(
        command.getFromMembershipId());

    if (!membershipStatus.isValid()) {
      return null;
    }

    // 2. 잔액 존재하는지 확인 (money service)
    MoneyInfo moneyInfo = moneyPort.getMoneyInfo(command.getFromMembershipId());

    // 잔액이 부족한 경우 -> 충전이 필요한 경우
    if (moneyInfo.getBalance() < command.getAmount()) {
      // 2-1. 잔액이 충분하지 않다면, 충전 요청 (money service)
      //   command.getAmount() - moneyInfo.getBalance()
      // 만원 단위로 올림하는 Math 함수
      int rechargeAmount =
          (int) Math.ceil((command.getAmount() - moneyInfo.getBalance()) / 10000.0) * 10000;
      boolean moneyResult = moneyPort.requestMoneyRecharging(command.getFromMembershipId(),
          rechargeAmount);

      if (!moneyResult) {
        return null;
      }
    }

    // 3. 송금 타입 (고객/은행)
    if (command.getRemittanceType() == 0) {
      // 3-1. 내부 고객일 경우
      // from 고객 머니 감액, to 고객 머니 증액 (money service)
      boolean remittanceResult1 = false;
      boolean remittanceResult2 = false;

      remittanceResult1 = moneyPort.requestMoneyDecrease(command.getFromMembershipId(),
          command.getAmount());
      remittanceResult2 = moneyPort.requestMoneyIncrease(command.getToMembershipId(),
          command.getAmount());

      if (!remittanceResult1 || !remittanceResult2) {
        return null;
      }


    } else if (command.getRemittanceType() == 1) {
      // 3-2. 외부 은행 계좌
      // 외부 은행 계좌가 적절한지 확인 (banking service)
      // 법인계좌 -> 외부 은행 계좌 펌뱅킹 요청 (banking service)
      boolean remittanceResult = bankingPort.requestFirmbanking(command.getToBankName(),
          command.getToBankAccountNumber(),
          command.getAmount());

      if(!remittanceResult) {
        return null;
      }
    }

    // 4. 송금 요청 상태를 성공으로 기록 (persistence layer)
    entity.setRemittanceStatus("success");
    boolean result = requestRemittancePort.saveRemittanceRequestHistory(entity);

    if (result) {
      return mapper.mapToDomainEntity(entity);
    }
    return null;
  }
}