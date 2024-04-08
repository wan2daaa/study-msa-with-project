package me.wane.membership.application.port.in;

import me.wane.membership.common.UseCase;
import me.wane.membership.domain.Membership;

@UseCase
public interface ModifyMembershipUseCase {

  //command 란 어떤 명령 바깥에서 안으로 들어오는데 있어서 이 request를 그대로 주는게 아니라 중간에서 command 라는 개념을 사용해서 request 를 command 로 바꿈
  Membership modifyMembership(ModifyMembershipCommand command);

}
