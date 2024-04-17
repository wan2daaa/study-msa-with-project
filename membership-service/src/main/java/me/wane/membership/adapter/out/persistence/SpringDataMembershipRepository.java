package me.wane.membership.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {

  List<MembershipJpaEntity> findAllByAddress(String address);
}
