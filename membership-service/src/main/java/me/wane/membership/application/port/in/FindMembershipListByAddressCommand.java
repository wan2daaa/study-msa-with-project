package me.wane.membership.application.port.in;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindMembershipListByAddressCommand extends SelfValidating<FindMembershipListByAddressCommand> {

  @NotNull
  private final String address;

  public FindMembershipListByAddressCommand(String address) {
    this.address = address; //관악구, 서초구, 강남구
    this.validateSelf();
  }
}
