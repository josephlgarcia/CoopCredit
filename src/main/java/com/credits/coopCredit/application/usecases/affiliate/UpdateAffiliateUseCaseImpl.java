package com.credits.coopCredit.application.usecases.affiliate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.ports.in.affiliate.UpdateAffiliateUseCase;
import com.credits.coopCredit.domain.ports.out.AffiliateRepositoryPort;

@Service
public class UpdateAffiliateUseCaseImpl implements UpdateAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepository;

    public UpdateAffiliateUseCaseImpl(AffiliateRepositoryPort affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    @Transactional
    public Affiliate update(Long id, String nombre, Double salario, String estado) {
        Affiliate affiliate = affiliateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Afiliado no encontrado"));

        if (nombre != null && !nombre.trim().isEmpty()) {
            affiliate.setNombre(nombre);
        }

        if (salario != null) {
            if (salario <= 0) {
                throw new IllegalArgumentException("El salario debe ser mayor a cero");
            }
            affiliate.setSalario(salario);
        }

        if (estado != null && (estado.equals("ACTIVO") || estado.equals("INACTIVO"))) {
            affiliate.setEstado(estado);
        }

        return affiliateRepository.save(affiliate);
    }
}
