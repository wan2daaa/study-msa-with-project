package me.wane.money.adapter.axon.event;

import lombok.*;
import me.wane.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class IncreaseMemberMoneyEvent extends SelfValidating<IncreaseMemberMoneyEvent> {

  private String aggregateIdentifier;
  private String targetMembershipId;
  private int amount;


  public IncreaseMemberMoneyEvent(String aggregateIdentifier, String targetMembershipId, int amount) {
    this.aggregateIdentifier = aggregateIdentifier;
    this.targetMembershipId = targetMembershipId;
    this.amount = amount;
    this.validateSelf();
  }
}
