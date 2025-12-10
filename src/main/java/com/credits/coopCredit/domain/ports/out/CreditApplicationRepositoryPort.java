package com.credits.coopCredit.domain.ports.out;

import com.credits.coopCredit.domain.model.CreditApplication;
import java.util.List;
import java.util.Optional;

public interface CreditApplicationRepositoryPort {
    CreditApplication save(CreditApplication application);

    Optional<CreditApplication> findById(Long id);

    List<CreditApplication> findAll();

    List<CreditApplication> findByEstado(String estado);

    List<CreditApplication> findByAffiliateId(Long affiliateId);
}
