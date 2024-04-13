package me.wane.money.adapter.in.web;

import lombok.RequiredArgsConstructor;
import me.wane.common.WebAdapter;
import me.wane.money.application.port.in.*;
import me.wane.money.domain.MoneyChangingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RequiredArgsConstructor
@RestController
public class RequestMoneyChangingController {

  private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;
  private final DecreaseMoneyRequestUseCase decreaseMoneyRequestUseCase;
  private final CreateMemberMoneyUseCase createMemberMoneyUseCase;

  @PostMapping(path = "/money/increase")
  MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
    IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
        .targetMembershipId(request.getTargetMembershipId())
        .amount(request.getAmount())
        .build();

    MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

    // MoneyChangingRequest -> MoneyChangingResultDetail
    MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
        moneyChangingRequest.getMoneyChangingRequestId(),
        0,
        0,
        moneyChangingRequest.getChangingMoneyAmount());
    return resultDetail;
  }

  @PostMapping(path = "/money/increase-asnyc")
  MoneyChangingResultDetail increaseMoneyChangingRequestAsync(@RequestBody IncreaseMoneyChangingRequest request) {
    IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
        .targetMembershipId(request.getTargetMembershipId())
        .amount(request.getAmount())
        .build();

    MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

    // MoneyChangingRequest -> MoneyChangingResultDetail
    MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
        moneyChangingRequest.getMoneyChangingRequestId(),
        0,
        0,
        moneyChangingRequest.getChangingMoneyAmount());
    return resultDetail;
  }


  @PostMapping(path = "/money/decrease")
  ResponseEntity<MoneyChangingResultDetail> decreaseMoneyChangingRequest(
      @RequestBody DecreaseMoneyChangingRequest request) {
    DecreaseMoneyRequestCommand command = DecreaseMoneyRequestCommand.builder()
        .targetMembershipId(request.getTargetMembershipId())
        .amount(request.getAmount())
        .build();

    MoneyChangingRequest moneyChangingRequest = decreaseMoneyRequestUseCase.decreaseMoneyRequest(command);

    // MoneyChangingRequest -> MoneyChangingResultDetail
    MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
        moneyChangingRequest.getMoneyChangingRequestId(),
        1,
        0,
        moneyChangingRequest.getChangingMoneyAmount());
    return ResponseEntity.ok(resultDetail);
  }

  @PostMapping("/money/create-memeber-money")
  void createMemberMoney(@RequestBody CreateMemberMoneyRequest request) {
    createMemberMoneyUseCase.createMemberMoney(
        CreateMemberMoneyCommand.builder()
            .targetMembershipId(request.getTargetMembershipId())
            .build()
    );
  }

  @PostMapping("/money/increase-eda")
  void increaseMoneyRequestByEvent(@RequestBody IncreaseMoneyChangingRequest request) {
    IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
        .targetMembershipId(request.getTargetMembershipId())
        .amount(request.getAmount())
        .build();

    increaseMoneyRequestUseCase.increaseMoneyRequestByEvent(command);
  }

}
