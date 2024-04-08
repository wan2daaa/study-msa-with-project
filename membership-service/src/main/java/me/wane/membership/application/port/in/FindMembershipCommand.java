package me.wane.membership.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindMembershipCommand extends SelfValidating<FindMembershipCommand> {

  @NotNull
  private final String membershipId;

  public FindMembershipCommand(String membershipId) {
    this.membershipId = membershipId;
    this.validateSelf();
  }
}
