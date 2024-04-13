package me.wane.money.adapter.axon.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.wane.common.SelfValidating;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class IncreaseMemberMoneyCommand extends SelfValidating<IncreaseMemberMoneyCommand> {

  @NotNull
  @TargetAggregateIdentifier
  private String aggregateIdentifier;

  @NotNull
  private String membershipId;

  @NotNull
  private int amount;

  public IncreaseMemberMoneyCommand(String aggregateIdentifier, String membershipId, int amount) {
    this.aggregateIdentifier = aggregateIdentifier;
    this.membershipId = membershipId;
    this.amount = amount;

    this.validateSelf();
  }

}