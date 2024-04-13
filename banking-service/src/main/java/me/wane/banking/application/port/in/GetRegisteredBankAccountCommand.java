package me.wane.banking.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class GetRegisteredBankAccountCommand extends SelfValidating<GetRegisteredBankAccountCommand> {

  @NotNull
  private final String membershipId;

  public GetRegisteredBankAccountCommand(String membershipId) {
    this.membershipId = membershipId;
    this.validateSelf();
  }
}
