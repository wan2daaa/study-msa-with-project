package me.wane.membership.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.common.WebAdapter;
import me.wane.membership.application.port.in.RegisterMembershipCommand;
import me.wane.membership.application.port.in.RegisterMembershipUseCase;
import me.wane.membership.domain.Membership;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class RegisterMembershipController {

  private final RegisterMembershipUseCase registerMembershipUseCase;

  @PostMapping("/membership/register")
  Membership registerMembership(@RequestBody RegisterMembershipRequest request) {

    RegisterMembershipCommand command = RegisterMembershipCommand.builder()
        .name(request.getName())
        .address(request.getAddress())
        .email(request.getEmail())
        .isValid(true)
        .isCorp(request.isCorp())
        .build();

    return registerMembershipUseCase.registerMembership(command);
  }

}
