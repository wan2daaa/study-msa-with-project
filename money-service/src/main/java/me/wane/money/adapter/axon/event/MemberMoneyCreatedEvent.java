package me.wane.money.adapter.axon.event;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.wane.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class MemberMoneyCreatedEvent extends SelfValidating<MemberMoneyCreatedEvent> {

  @NotNull
  private String membershipId;

  public MemberMoneyCreatedEvent(@NotNull String membershipId) {
    this.membershipId = membershipId;
    this.validateSelf();
  }
}
