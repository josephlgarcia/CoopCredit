package com.credits.coopCredit.domain.ports.in.affiliate;

import com.credits.coopCredit.domain.model.Affiliate;
import java.time.LocalDate;

public interface CreateAffiliateUseCase {
    Affiliate create(String documento, String nombre, Double salario, LocalDate fechaAfiliacion);
}
