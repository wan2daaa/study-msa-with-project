package me.wane.remittance.application.port.in;

import java.util.List;
import me.wane.remittance.domain.RemittanceRequest;

public interface FindRemittanceUseCase {

  List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command);
}
