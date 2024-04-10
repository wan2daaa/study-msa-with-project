package me.wane.remittance.application.port.in;

import me.wane.remittance.domain.RemittanceRequest;

public interface RequestRemittanceUseCase {
  RemittanceRequest requestRemittance(RequestRemittanceCommand command);
}