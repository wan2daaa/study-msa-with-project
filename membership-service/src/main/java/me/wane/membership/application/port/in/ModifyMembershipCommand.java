package me.wane.membership.application.port.in;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ModifyMembershipCommand extends SelfValidating<ModifyMembershipCommand> {

  @NotNull
  private final String membershipId;

  @NotNull
  private final String name;

  @NotNull
  private final String email;

  @NotNull
  @NotBlank
  private final String address;

  @AssertTrue // 나중에 로직 상 바뀔 수 있지만, 일단 항상 true를 반환하게 설정하자
  private final boolean isValid;


  private final boolean isCorp;

  public ModifyMembershipCommand(String membershipId, String name, String email, String address, boolean isValid,
      boolean isCorp) {
    this.membershipId = membershipId;
    this.name = name;
    this.email = email;
    this.address = address;
    this.isValid = isValid;
    this.isCorp = isCorp;

    this.validateSelf();
  }
}
