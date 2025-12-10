package com.credits.coopCredit.domain.ports.in.affiliate;

import com.credits.coopCredit.domain.model.Affiliate;
import java.util.Optional;

public interface GetAffiliateUseCase {
    Optional<Affiliate> getById(Long id);

    Optional<Affiliate> getByDocumento(String documento);
}
