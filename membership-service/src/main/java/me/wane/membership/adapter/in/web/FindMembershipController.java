package me.wane.membership.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.membership.application.port.in.FindMembershipCommand;
import me.wane.membership.application.port.in.FindMembershipUseCase;
import me.wane.membership.common.WebAdapter;
import me.wane.membership.domain.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class FindMembershipController {

  private final FindMembershipUseCase findMembershipUseCase;

  @GetMapping("/membership/{membershipId}")
  ResponseEntity<Membership> findMembershipByMembershipId(@PathVariable String membershipId) {

    FindMembershipCommand command = FindMembershipCommand.builder()
        .membershipId(membershipId)
        .build();

    return ResponseEntity.ok(findMembershipUseCase.findMembership(command));
  }
}
