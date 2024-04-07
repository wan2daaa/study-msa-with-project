package me.wane.membership.application.port.in;

import me.wane.membership.common.UseCase;
import me.wane.membership.domain.Membership;

@UseCase
public interface FindMembershipUseCase {

  Membership findMembership(FindMembershipCommand command);
}
