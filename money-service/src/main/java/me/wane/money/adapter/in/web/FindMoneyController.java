package me.wane.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.common.WebAdapter;
import me.wane.money.application.port.in.MembershipIdRequestCommand;
import me.wane.money.application.port.in.FindMoneyRequestUseCase;
import me.wane.money.domain.MoneyInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class FindMoneyController {

  private final FindMoneyRequestUseCase findMoneyRequestUseCase;

  @GetMapping("/money/{membershipId}")
  ResponseEntity<MoneyInfo> findMoneyInfoByMembershipId(@PathVariable String membershipId) {

    MembershipIdRequestCommand command = MembershipIdRequestCommand.of(membershipId);

    return ResponseEntity.ok(findMoneyRequestUseCase.findMoneyAmountByMembershipId(command));

  }

}
