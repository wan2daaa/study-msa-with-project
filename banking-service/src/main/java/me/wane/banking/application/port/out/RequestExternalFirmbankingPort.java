package me.wane.banking.application.port.out;

import me.wane.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import me.wane.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {

  FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest request);
}
