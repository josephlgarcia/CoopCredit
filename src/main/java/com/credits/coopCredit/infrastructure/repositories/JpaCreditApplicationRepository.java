package com.credits.coopCredit.infrastructure.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.credits.coopCredit.infrastructure.entities.CreditApplicationEntity;

@Repository
public interface JpaCreditApplicationRepository extends JpaRepository<CreditApplicationEntity, Long> {
    List<CreditApplicationEntity> findByEstado(String estado);

    List<CreditApplicationEntity> findByAffiliateId(Long affiliateId);
}
