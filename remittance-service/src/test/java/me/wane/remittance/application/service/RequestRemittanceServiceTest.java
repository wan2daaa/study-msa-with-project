package me.wane.remittance.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;
import me.wane.remittance.adapter.out.persistence.RemittanceRequestMapper;
import me.wane.remittance.application.port.in.RequestRemittanceCommand;
import me.wane.remittance.application.port.out.RequestRemittancePort;
import me.wane.remittance.application.port.out.banking.BankingPort;
import me.wane.remittance.application.port.out.membership.MembershipPort;
import me.wane.remittance.application.port.out.membership.MembershipStatus;
import me.wane.remittance.application.port.out.money.MoneyInfo;
import me.wane.remittance.application.port.out.money.MoneyPort;
import me.wane.remittance.domain.RemittanceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequestRemittanceServiceTest {

  // Inject Mocks
  // @InjectMocks
  @InjectMocks  // 목 들을 주입하는 클래스
  private RequestRemittanceService requestRemittanceService;

  // Inject
  // @Mock
  @Mock
  private RequestRemittancePort requestRemittancePort;
  @Mock
  private RemittanceRequestMapper mapper;
  @Mock
  private MembershipPort membershipPort;
  @Mock
  private MoneyPort moneyPort;
  @Mock
  private BankingPort bankingPort;

  @BeforeEach
  public void setUp() {
    // 이 라인이 InjectMocks 가 포함된 클래스에
    // @Mock 들을 주입 시켜줌
    MockitoAnnotations.openMocks(this);

    /**
     * @NOTE
     * private final 필드의 경우, setter 를 통해 주입할 수 없기 때문에,
     * Reflection or Constructor 를 통해 주입해야 한다
     */

    requestRemittanceService = new RequestRemittanceService(
        requestRemittancePort, mapper, membershipPort, moneyPort, bankingPort
    );
  }

  private static Stream<RequestRemittanceCommand> provideRequestRemittanceCommand() {
    return Stream.of(
        RequestRemittanceCommand.builder()
            .fromMembershipId("5")
            .toMembershipId("4")
            .toBankName("bank22")
            .remittanceType(0)
            .toBankAccountNumber("12344-5123-333")
            .amount(150000)
            .build()
    );
  }

  @Test
  public void test() {
    System.out.println("requestRemittanceService = " + requestRemittanceService);
  }

  // 송금 요청을 한 고객이 유효하지 않은 경우
  @ParameterizedTest
  @MethodSource("provideRequestRemittanceCommand")
  public void testRequestRemittanceServiceWhenFromMembershipInvalid(RequestRemittanceCommand testCommand) {

    // 1. 먼저, 어떤 결과가 나올지에 대해서 정의
    RemittanceRequest want = null;

    // 2. Mocking을 위한 dummy data가 있다면, 그 data는 먼저 만들어줌


    // 3. 그 결과를 위해, Mocking 해줌
    when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
        .thenReturn(null);

    when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
        .thenReturn(new MembershipStatus(testCommand.getFromMembershipId(), false));

    // 4. 그리고 그 Mocking 된 mock 들을 사용해서 테스트를 진행
    RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

    // 5. Verify를 통해서, 테스트가 잘 진행되었는지 확인
    verify(requestRemittancePort, times(1)).createRemittanceRequestHistory(testCommand);
    verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());

    // 6. Assert 를 통해, 최종적으로 이 테스트가 잘 진행되었는지 확인
    assertEquals(want, got);

  }

  // 잔액이 중분하지 않은 경우
  @ParameterizedTest
  @MethodSource("provideRequestRemittanceCommand")
  public void testRequestRemittanceServiceWithWhenNotEnoughMoney(RequestRemittanceCommand testCommand) {

    // 1. 먼저, 어떤 결과가 나올지에 대해서 정의
    RemittanceRequest want = null;

    // 2. Mocking을 위한 dummy data가 있다면, 그 data는 먼저 만들어줌
    MoneyInfo dummyMoneyInfo = new MoneyInfo(
        testCommand.getFromMembershipId(),
        10000
    );

    // 3. 그 결과를 위해, Mocking 해줌
    when(requestRemittancePort.createRemittanceRequestHistory(testCommand))
        .thenReturn(null);

    when(membershipPort.getMembershipStatus(testCommand.getFromMembershipId()))
        .thenReturn(new MembershipStatus(testCommand.getFromMembershipId(), true));

    when(moneyPort.getMoneyInfo(testCommand.getFromMembershipId()))
        .thenReturn(dummyMoneyInfo);

    int rechargeAmount = (int) Math.ceil((testCommand.getAmount() - dummyMoneyInfo.getBalance()) / 10000.0) * 10000;
    when(moneyPort.requestMoneyRecharging(testCommand.getFromMembershipId(), rechargeAmount))
        .thenReturn(false);

    // 4. 그리고 그 Mocking 된 mock 들을 사용해서 테스트를 진행
    RemittanceRequest got = requestRemittanceService.requestRemittance(testCommand);

    // 5. Verify를 통해서, 테스트가 잘 진행되었는지 확인
    verify(requestRemittancePort, times(1)).createRemittanceRequestHistory(testCommand);
    verify(membershipPort, times(1)).getMembershipStatus(testCommand.getFromMembershipId());
    verify(moneyPort, times(1)).getMoneyInfo(testCommand.getFromMembershipId());
    verify(moneyPort, times(1)).requestMoneyRecharging(testCommand.getFromMembershipId(), rechargeAmount);

    // 6. Assert 를 통해, 최종적으로 이 테스트가 잘 진행되었는지 확인
    assertEquals(want, got);

  }

}