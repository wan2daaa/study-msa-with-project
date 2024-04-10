package me.wane.remittance.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Remittance { //송금이 완료된 건에 대한 클래스
  @Getter
  private final String remittanceId;

  @Value
  public static class RemittanceId {
    public RemittanceId(String value) {
      this.remittanceId = value;
    }

    String remittanceId;
  }

  public static Remittance generateRemittance(
  ){
    return new Remittance(
        ""
    );
  }
}