package me.wane.remittance.adapter.out.persistence;

import me.wane.remittance.domain.RemittanceRequest;
import me.wane.remittance.domain.RemittanceRequest.Amount;
import me.wane.remittance.domain.RemittanceRequest.RemittanceFromMembershipId;
import me.wane.remittance.domain.RemittanceRequest.RemittanceRequestId;
import me.wane.remittance.domain.RemittanceRequest.RemittanceStatus;
import me.wane.remittance.domain.RemittanceRequest.RemittanceType;
import me.wane.remittance.domain.RemittanceRequest.ToBankAccountNumber;
import me.wane.remittance.domain.RemittanceRequest.ToBankName;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RemittanceRequestMapper {
  public RemittanceRequest mapToDomainEntity(RemittanceRequestJpaEntity remittanceRequestJpaEntity) {
    return RemittanceRequest.generateRemittanceRequest(
        new RemittanceRequestId(remittanceRequestJpaEntity.getRemittanceRequestId() + ""),
        new RemittanceFromMembershipId(remittanceRequestJpaEntity.getFromMembershipId()),
        new ToBankName(remittanceRequestJpaEntity.getToBankName()),
        new ToBankAccountNumber(remittanceRequestJpaEntity.getToBankAccountNumber()),
        new RemittanceType(remittanceRequestJpaEntity.getRemittanceType()),
        new Amount(remittanceRequestJpaEntity.getAmount()),
        new RemittanceStatus(remittanceRequestJpaEntity.getRemittanceStatus())
    );
  }
}