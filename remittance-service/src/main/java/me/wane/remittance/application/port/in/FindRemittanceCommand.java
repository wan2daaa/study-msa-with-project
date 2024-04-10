package me.wane.remittance.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class FindRemittanceCommand extends SelfValidating<FindRemittanceCommand> {

  @NotNull
  private String membershipId; //fromMembershipId

  public FindRemittanceCommand(String membershipId) {
    this.membershipId = membershipId;

    this.validateSelf();
  }
}
