package com.credits.coopCredit.domain.ports.out;

import java.util.Optional;

import com.credits.coopCredit.domain.model.Affiliate;

public interface AffiliateRepositoryPort {
    Affiliate save(Affiliate affiliate);
    Optional<Affiliate> findById(Long id);
    Optional<Affiliate> findByDocumento(String documento);
}
