package me.wane.remittance.adapter.in.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.wane.common.WebAdapter;
import me.wane.remittance.application.port.in.FindRemittanceCommand;
import me.wane.remittance.application.port.in.FindRemittanceUseCase;
import me.wane.remittance.domain.RemittanceRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindRemittanceHistoryController {

  private final FindRemittanceUseCase findRemittanceUseCase;
  @GetMapping( "/remittance/{membershipId}")
  List<RemittanceRequest> findRemittanceHistory(@PathVariable String membershipId) {
    FindRemittanceCommand command = FindRemittanceCommand.builder()
        .membershipId(membershipId)
        .build();

    try {
      Thread.sleep(20000); //20 초
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }


    return findRemittanceUseCase.findRemittanceHistory(command);
  }
}