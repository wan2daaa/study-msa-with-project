package me.wane.banking.adapter.axon.command;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.wane.common.SelfValidating;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateFirmbankingRequestCommand extends SelfValidating<UpdateFirmbankingRequestCommand> {

  @NotNull
  @TargetAggregateIdentifier
  private String firmbankingRequestAggregateIdentifier;

  @NotNull
  private int status;

}
