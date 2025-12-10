package com.credits.coopCredit.infrastructure.adapters;

import org.springframework.stereotype.Component;

import com.credits.coopCredit.domain.model.CreditEvaluation;
import com.credits.coopCredit.domain.ports.out.CreditEvaluationRepositoryPort;
import com.credits.coopCredit.infrastructure.entities.CreditEvaluationEntity;
import com.credits.coopCredit.infrastructure.repositories.JpaCreditEvaluationRepository;

@Component
public class CreditEvaluationRepositoryAdapter implements CreditEvaluationRepositoryPort {

    private final JpaCreditEvaluationRepository repo;

    public CreditEvaluationRepositoryAdapter(JpaCreditEvaluationRepository repo) {
        this.repo = repo;
    }

    @Override
    public CreditEvaluation save(CreditEvaluation evaluation) {
        CreditEvaluationEntity e = new CreditEvaluationEntity();
        e.setId(evaluation.getId());
        e.setScore(evaluation.getScore());
        e.setNivelRiesgo(evaluation.getNivelRiesgo());
        e.setDetalle(evaluation.getDetalle());
        e.setFecha(evaluation.getFecha());
        e.setDecision(evaluation.getDecision());
        e.setMotivo(evaluation.getMotivo());
        CreditEvaluationEntity saved = repo.save(e);
        evaluation.setId(saved.getId());
        return evaluation;
    }
}
