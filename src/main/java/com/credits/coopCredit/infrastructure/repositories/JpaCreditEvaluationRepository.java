package com.credits.coopCredit.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.credits.coopCredit.infrastructure.entities.CreditEvaluationEntity;

@Repository
public interface JpaCreditEvaluationRepository extends JpaRepository<CreditEvaluationEntity, Long> {
}
