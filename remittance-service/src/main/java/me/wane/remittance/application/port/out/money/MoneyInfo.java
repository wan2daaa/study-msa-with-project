package me.wane.remittance.application.port.out.money;


import lombok.Data;

//송금 서비스에서 필요한 머니의 인포
@Data
public class MoneyInfo {

  private String membershipId;
  private int balance; //잔액

}
