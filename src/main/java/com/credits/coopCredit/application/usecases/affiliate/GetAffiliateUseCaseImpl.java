package com.credits.coopCredit.application.usecases.affiliate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.ports.in.affiliate.GetAffiliateUseCase;
import com.credits.coopCredit.domain.ports.out.AffiliateRepositoryPort;
import java.util.Optional;

@Service
public class GetAffiliateUseCaseImpl implements GetAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepository;

    public GetAffiliateUseCaseImpl(AffiliateRepositoryPort affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Affiliate> getById(Long id) {
        return affiliateRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Affiliate> getByDocumento(String documento) {
        return affiliateRepository.findByDocumento(documento);
    }
}
