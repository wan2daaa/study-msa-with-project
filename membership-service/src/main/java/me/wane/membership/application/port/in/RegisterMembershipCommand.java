package me.wane.membership.application.port.in;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.wane.membership.common.SelfValidating;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {

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

  public RegisterMembershipCommand(String name, String email, String address, boolean isValid,
      boolean isCorp) {
    this.name = name;
    this.email = email;
    this.address = address;
    this.isValid = isValid;
    this.isCorp = isCorp;
    this.validateSelf();
  }
}
