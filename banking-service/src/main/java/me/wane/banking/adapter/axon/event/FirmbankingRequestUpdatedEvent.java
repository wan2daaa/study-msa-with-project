package me.wane.banking.adapter.axon.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FirmbankingRequestUpdatedEvent {

  private int firmbankingStatus;

}
