package com.credits.coopCredit.domain.ports.in.credit;

import com.credits.coopCredit.domain.model.CreditApplication;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada para consultar solicitudes de crédito.
 */
public interface GetCreditApplicationUseCase {

    Optional<CreditApplication> getById(Long id);

    List<CreditApplication> getAll();

    List<CreditApplication> getByEstado(String estado);

    List<CreditApplication> getByAffiliate(Long affiliateId);
}
