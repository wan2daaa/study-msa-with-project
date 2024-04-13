package me.wane.banking.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, Long> {

  List<RegisteredBankAccountJpaEntity> findByMembershipId(String membershipId);

}
