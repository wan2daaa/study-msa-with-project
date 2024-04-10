package me.wane.remittance.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Builder
@Entity
@Table(name = "request_remittance")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemittanceRequestJpaEntity {
  @Id
  @GeneratedValue
  private Long remittanceRequestId;

  private String fromMembershipId;

  private String toMembershipId;

  private String toBankName;

  private String toBankAccountNumber;

  private int remittanceType; // 0: membership(내부고객), 1: bank(외부 은행 계좌)

  private int amount;

  private String remittanceStatus;

}