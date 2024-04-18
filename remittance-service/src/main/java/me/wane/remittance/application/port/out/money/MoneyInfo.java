package me.wane.remittance.application.port.out.money;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//송금 서비스에서 필요한 머니의 인포
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyInfo {

  private String membershipId;
  private int balance; //잔액

}
