package me.wane.money.application.port.in;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class DecreaseMoneyRequestCommand extends SelfValidating<DecreaseMoneyRequestCommand> {

  @NotNull
  private final String targetMembershipId;

  @NotNull
  private final int amount;


  public DecreaseMoneyRequestCommand(@NotNull String targetMembershipId, @NotNull int amount) {
    this.targetMembershipId = targetMembershipId;
    this.amount = amount;
    this.validateSelf();
  }
}
