package com.credits.coopCredit.domain.ports.out;

import com.credits.coopCredit.domain.model.CreditEvaluation;

public interface CreditEvaluationRepositoryPort {
    CreditEvaluation save(CreditEvaluation evaluation);
}
