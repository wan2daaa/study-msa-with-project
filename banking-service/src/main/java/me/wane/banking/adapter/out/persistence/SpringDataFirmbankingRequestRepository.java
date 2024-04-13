package me.wane.banking.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpringDataFirmbankingRequestRepository extends JpaRepository<FirmbankingRequestJpaEntity, Long> {

  @Query("select fr from FirmbankingRequestJpaEntity fr where fr.aggregateIdentifier = :aggregateIdentifier")
  List<FirmbankingRequestJpaEntity> findByAggregateIdentifier(String aggregateIdentifier);

}
