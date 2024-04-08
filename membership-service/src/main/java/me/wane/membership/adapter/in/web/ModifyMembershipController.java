package me.wane.membership.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.membership.application.port.in.FindMembershipCommand;
import me.wane.membership.application.port.in.ModifyMembershipCommand;
import me.wane.membership.application.port.in.ModifyMembershipUseCase;
import me.wane.membership.common.WebAdapter;
import me.wane.membership.domain.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class ModifyMembershipController {

  private final ModifyMembershipUseCase modifyMembershipUseCase;

  @PostMapping("/membership/modify")
  ResponseEntity<Membership> modifyMembershipByMembershipId(
      @RequestBody ModifyMembershipRequest request
  ) {

    ModifyMembershipCommand command = ModifyMembershipCommand.builder()
        .membershipId(request.getMembershipId())
        .name(request.getName())
        .email(request.getEmail())
        .address(request.getAddress())
        .isValid(request.isValid())
        .isCorp(request.isCorp())
        .build();

    return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
  }
}
