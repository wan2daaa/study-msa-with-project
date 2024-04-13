package me.wane.banking.application.port.in;

import me.wane.banking.domain.FirmbankingRequest;
import me.wane.common.UseCase;

@UseCase
public interface UpdateFirmbankingUseCase {

  void updateFirmbankingByEvent(UpdateFirmbankingCommand command);
}
