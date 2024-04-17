package me.wane.money.aggregation.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class GetMoneySumByAddressCommand extends SelfValidating<GetMoneySumByAddressCommand> {
  @NotNull
  private final String address;

  public GetMoneySumByAddressCommand(@NotNull String address) {
    this.address = address;
    this.validateSelf();
  }
}