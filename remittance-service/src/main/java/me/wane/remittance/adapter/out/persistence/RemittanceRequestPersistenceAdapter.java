package me.wane.remittance.adapter.out.persistence;

import java.util.List;
import me.wane.common.PersistenceAdapter;
import me.wane.remittance.application.port.in.FindRemittanceCommand;
import me.wane.remittance.application.port.in.RequestRemittanceCommand;
import me.wane.remittance.application.port.out.FindRemittancePort;
import me.wane.remittance.application.port.out.RequestRemittancePort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RemittanceRequestPersistenceAdapter implements RequestRemittancePort,
    FindRemittancePort {

  private final SpringDataRemittanceRequestRepository remittanceRequestRepository;

  @Override
  public RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command) {
    return remittanceRequestRepository.save(
        RemittanceRequestJpaEntity.builder()
            .fromMembershipId(command.getFromMembershipId())
            .toMembershipId(command.getToMembershipId())
            .toBankName(command.getToBankName())
            .toBankAccountNumber(command.getToBankAccountNumber())
            .amount(command.getAmount())
            .remittanceType(command.getRemittanceType())
            .build()
    );
  }

  @Override
  public boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity) {
    remittanceRequestRepository.save(entity);
    return true;
  }

  @Override
  public List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command) {

    //JPA
    return List.of();
  }
}