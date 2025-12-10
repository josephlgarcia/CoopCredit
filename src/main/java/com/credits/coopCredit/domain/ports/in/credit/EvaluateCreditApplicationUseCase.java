package com.credits.coopCredit.domain.ports.in.credit;

import com.credits.coopCredit.domain.model.CreditApplication;

/**
 * Puerto de entrada para evaluar solicitudes de crédito.
 */
public interface EvaluateCreditApplicationUseCase {
    CreditApplication evaluate(Long id);
}
