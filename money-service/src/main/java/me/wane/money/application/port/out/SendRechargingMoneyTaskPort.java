package me.wane.money.application.port.out;

import me.wane.common.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {

  void sendRechargingMoneyTaskPort(
      RechargingMoneyTask task
  );

}
