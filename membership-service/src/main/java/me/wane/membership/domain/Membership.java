package me.wane.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Membership {

  @Getter
  private final String membershipId;
  @Getter
  private final String name;
  @Getter
  private final String email;
  @Getter
  private final String address;
  @Getter
  private final boolean isValid;
  @Getter
  private final boolean isCorp;

  //일반적으로
  //member 정의
  //getter, setter @Data

  //클린 아키텍처 관점에서

  //Membership은 오염이 되면 안되는 클래스. 고객 정보. 핵심 도메인
  //새롭게 클래스 생성하기 어렵게 됨
  // 각각의 정보를 value로 관리할 수있게 만들자

  public static Membership generateMember(
      MembershipId memberShipId,
      MembershipName membershipName,
      MembershipEmail membershipEmail,
      MembershipAddress membershipAddress,
      MembershipIsValid membershipIsValid,
      MembershipIsCorp membershipIsCorp
      ) {
    return new Membership(
        memberShipId.membershipId,
        membershipName.nameValue,
        membershipEmail.emailValue,
        membershipAddress.addressValue,
        membershipIsValid.isValidValue,
        membershipIsCorp.isCorpValue
    );
  }

  @Value
  public static class MembershipId {

    public MembershipId(String value) {
      this.membershipId = value;
    }

    String membershipId;
  }

  @Value
  public static class MembershipName {

    public MembershipName(String value) {
      this.nameValue = value;
    }

    String nameValue;
  }

  @Value
  public static class MembershipEmail {

    public MembershipEmail(String value) {
      this.emailValue = value;
    }

    String emailValue;
  }

  @Value
  public static class MembershipAddress {

    public MembershipAddress(String value) {
      this.addressValue = value;
    }

    String addressValue;
  }

  @Value
  public static class MembershipIsValid {

    public MembershipIsValid(boolean value) {
      this.isValidValue = value;
    }

    boolean isValidValue;
  }

  @Value
  public static class MembershipIsCorp {

    public MembershipIsCorp(boolean value) {
      this.isCorpValue = value;
    }

    boolean isCorpValue;
  }


}
