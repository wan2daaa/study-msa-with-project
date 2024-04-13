package me.wane.banking.adapter.axon.event;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FirmbankingRequestCreateEvent {

  private String fromBankName;
  private String fromBankAccountNumber;

  private String toBankName;
  private String toBankAccountNumber;

  private int moneyAmount;

}
