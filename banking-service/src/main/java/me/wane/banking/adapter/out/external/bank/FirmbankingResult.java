package me.wane.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FirmbankingResult {

  private int resultCode ; //0 : 성공, 1: 실패

}
