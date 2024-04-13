package me.wane.payment.application.port.in;

import me.wane.payment.domain.Payment;

import java.util.List;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);

    // 원래대로라면,, command . start date, end date
    List<Payment> getNormalStatusPayments();

    void finishPayment(FinishSettlementCommand command);
}
