package me.wane.money.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.wane.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberMoneyCreatedCommand extends SelfValidating<MemberMoneyCreatedCommand> {

  @NotNull
  private String membershipId;

  public MemberMoneyCreatedCommand(@NotNull String membershipId) {
    this.membershipId = membershipId;
    this.validateSelf();
  }
}
