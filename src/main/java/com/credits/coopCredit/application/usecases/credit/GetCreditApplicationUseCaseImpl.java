package com.credits.coopCredit.application.usecases.credit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.credits.coopCredit.domain.model.CreditApplication;
import com.credits.coopCredit.domain.ports.in.credit.GetCreditApplicationUseCase;
import com.credits.coopCredit.domain.ports.out.CreditApplicationRepositoryPort;
import java.util.List;
import java.util.Optional;

@Service
public class GetCreditApplicationUseCaseImpl implements GetCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort repository;

    public GetCreditApplicationUseCaseImpl(CreditApplicationRepositoryPort repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<CreditApplication> getById(Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<CreditApplication> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CreditApplication> getByEstado(String estado) {
        return repository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    public List<CreditApplication> getByAffiliate(Long affiliateId) {
        return repository.findByAffiliateId(affiliateId);
    }
}
