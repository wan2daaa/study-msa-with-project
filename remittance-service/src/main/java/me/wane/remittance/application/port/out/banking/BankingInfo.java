package me.wane.remittance.application.port.out.banking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankingInfo {
  private String bankName;
  private String bankAccountNumber;
  private boolean isValid;

}
