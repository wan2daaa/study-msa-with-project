package me.wane.money.application.port.in;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateMemberMoneyCommand extends SelfValidating<CreateMemberMoneyCommand> {

  @NotNull
  private final String targetMembershipId;


  public CreateMemberMoneyCommand(@NotNull String targetMembershipId) {
    this.targetMembershipId = targetMembershipId;
    this.validateSelf();
  }
}
