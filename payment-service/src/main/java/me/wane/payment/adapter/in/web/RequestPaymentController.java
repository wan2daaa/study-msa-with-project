package me.wane.payment.adapter.in.web;

import me.wane.payment.application.port.in.FinishSettlementCommand;
import me.wane.payment.application.port.in.RequestPaymentCommand;
import me.wane.payment.application.port.in.RequestPaymentUseCase;
import me.wane.payment.domain.Payment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.common.WebAdapter;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {

  private final RequestPaymentUseCase requestPaymentUseCase;

  @PostMapping(path = "/payment/request")
  Payment requestPayment(PaymentRequest request) {
    return requestPaymentUseCase.requestPayment(
        new RequestPaymentCommand(
            request.getRequestMembershipId(),
            request.getRequestPrice(),
            request.getFranchiseId(),
            request.getFranchiseFeeRate()
        )
    );
  }

  @GetMapping(path = "/payment/normal-status")
  List<Payment> getNormalStatusPayments() {
    return requestPaymentUseCase.getNormalStatusPayments();
  }

  @PostMapping(path = "/payment/finish-settlement")
  void finishSettlement(@RequestBody FinishSettlementRequest request) {
    System.out.println("request.getPaymentId() = " + request.getPaymentId());
    requestPaymentUseCase.finishPayment(
        new FinishSettlementCommand(
            request.getPaymentId()
        )
    );
  }
}
