package me.wane.membership.application.port.in;

import java.util.List;
import me.wane.common.UseCase;
import me.wane.membership.domain.Membership;

@UseCase
public interface FindMembershipUseCase {

  Membership findMembership(FindMembershipCommand command);

  List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command);
}
