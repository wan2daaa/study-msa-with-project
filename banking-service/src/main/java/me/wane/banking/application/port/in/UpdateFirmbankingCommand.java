package me.wane.banking.application.port.in;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;


@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateFirmbankingCommand extends SelfValidating<UpdateFirmbankingCommand> {

  @NotNull
  private final String firmbankingAggregateIdentifier;

  @NotNull
  private final int status;

  public UpdateFirmbankingCommand(String firmbankingAggregateIdentifier, int status) {
    this.firmbankingAggregateIdentifier = firmbankingAggregateIdentifier;
    this.status = status;
    this.validateSelf();
  }
}