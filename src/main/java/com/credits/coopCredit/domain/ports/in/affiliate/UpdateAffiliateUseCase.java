package com.credits.coopCredit.domain.ports.in.affiliate;

import com.credits.coopCredit.domain.model.Affiliate;

public interface UpdateAffiliateUseCase {
    Affiliate update(Long id, String nombre, Double salario, String estado);
}
