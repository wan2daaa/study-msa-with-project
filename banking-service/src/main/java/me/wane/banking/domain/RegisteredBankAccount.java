package me.wane.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

  @Getter
  private final String registeredBankAccountId;
  @Getter
  private final String membershipId;
  @Getter
  private final String bankName; //enum
  @Getter
  private final String bankAccountNumber;
  @Getter
  private final boolean linkedStatusIsValid;

  //일반적으로
  //member 정의
  //getter, setter @Data

  //클린 아키텍처 관점에서

  //Membership은 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인
  //새롭게 클래스 생성하기 어렵게 됨
  // 각각의 정보를 value로 관리할 수있게 만들자

  public static RegisteredBankAccount generateMember(
      RegisteredBankAccountId registeredBankAccountId,
      MembershipId membershipId,
      BankName bankName,
      BankAccountNumber bankAccountNumber,
      LinkedStatusIsValid linkedStatusIsValid
  ) {
    return new RegisteredBankAccount(
        registeredBankAccountId.registeredBankAccountId,
        membershipId.membershipId,
        bankName.bankName,
        bankAccountNumber.bankAccountNumber,
        linkedStatusIsValid.linkedStatusIsValid
    );
  }

  @Value
  public static class RegisteredBankAccountId {

    public RegisteredBankAccountId(String value) {
      this.registeredBankAccountId = value;
    }

    String registeredBankAccountId;
  }

  @Value
  public static class MembershipId {

    public MembershipId(String value) {
      this.membershipId = value;
    }

    String membershipId;
  }

  @Value
  public static class BankName {

    public BankName(String value) {
      this.bankName = value;
    }

    String bankName;
  }

  @Value
  public static class BankAccountNumber {

    public BankAccountNumber(String value) {
      this.bankAccountNumber = value;
    }

    String bankAccountNumber;
  }

  @Value
  public static class LinkedStatusIsValid {

    public LinkedStatusIsValid(boolean value) {
      this.linkedStatusIsValid = value;
    }

    boolean linkedStatusIsValid;
  }


}
