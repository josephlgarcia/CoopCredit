package com.credits.coopCredit.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.credits.coopCredit.infrastructure.entities.AffiliateEntity;

@Repository
public interface JpaAffiliateRepository extends JpaRepository<AffiliateEntity, Long> {
    Optional<AffiliateEntity> findByDocumento(String documento);
}
