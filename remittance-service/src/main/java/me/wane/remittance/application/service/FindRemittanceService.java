package me.wane.remittance.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.common.UseCase;
import me.wane.remittance.adapter.out.persistence.RemittanceRequestMapper;
import me.wane.remittance.application.port.in.FindRemittanceCommand;
import me.wane.remittance.application.port.in.FindRemittanceUseCase;
import me.wane.remittance.application.port.out.FindRemittancePort;
import me.wane.remittance.application.port.out.RequestRemittancePort;
import me.wane.remittance.domain.RemittanceRequest;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {

  private final FindRemittancePort findRemittancePort;
  private final RemittanceRequestMapper mapper;


  @Override
  public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {

    // ~~~
    findRemittancePort.findRemittanceHistory(command);


    return List.of();
  }
}
