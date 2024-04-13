package me.wane.banking.adapter.axon.command;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegisteredBankAccountCommand {

  private String membershipId;
  private String bankName;
  private String bankAccountNumber;

}
