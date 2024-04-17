package me.wane.membership.adapter.in.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.common.WebAdapter;
import me.wane.membership.application.port.in.FindMembershipCommand;
import me.wane.membership.application.port.in.FindMembershipListByAddressCommand;
import me.wane.membership.application.port.in.FindMembershipUseCase;
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

  @GetMapping("/membership/address/{addressName}")
  ResponseEntity<List<Membership>> findMembershipByAddress(@PathVariable String addressName) {

    FindMembershipListByAddressCommand command = FindMembershipListByAddressCommand.builder()
        .address(addressName)
        .build();

    return ResponseEntity.ok(findMembershipUseCase.findMembershipListByAddress(command));
  }
}
