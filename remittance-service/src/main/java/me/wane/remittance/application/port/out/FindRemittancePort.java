package me.wane.remittance.application.port.out;

import java.util.List;
import me.wane.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import me.wane.remittance.application.port.in.FindRemittanceCommand;

public interface FindRemittancePort {

  List<RemittanceRequestJpaEntity> findRemittanceHistory(
      FindRemittanceCommand command);

}