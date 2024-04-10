package me.wane.remittance.application.port.out;

import me.wane.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import me.wane.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

  RemittanceRequestJpaEntity createRemittanceRequestHistory(
      RequestRemittanceCommand command);

  boolean saveRemittanceRequestHistory(
      RemittanceRequestJpaEntity entity);
}