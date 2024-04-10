package me.wane.remittance.application.port.in;

import me.wane.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RequestRemittanceCommand extends SelfValidating<RequestRemittanceCommand> {

  @NotNull
  private final String fromMembershipId;

  private String toMembershipId;

  @NotNull
  private final String toBankName;

  @NotNull
  @NotBlank
  private final String toBankAccountNumber;

  private int remittanceType; // 0 : membership(내부 고객) , 1: bank (외부 은행계좌)

  @NotNull
  @NotBlank
  private int amount;

  public RequestRemittanceCommand(String fromMembershipId, String toMembershipId, String toBankName,
      String toBankAccountNumber, int remittanceType, int amount) {
    this.fromMembershipId = fromMembershipId;
    this.toMembershipId = toMembershipId;
    this.toBankName = toBankName;
    this.toBankAccountNumber = toBankAccountNumber;
    this.remittanceType = remittanceType;
    this.amount = amount;
  }
}