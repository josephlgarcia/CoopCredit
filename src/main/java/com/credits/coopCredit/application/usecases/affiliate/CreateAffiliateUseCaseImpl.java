package com.credits.coopCredit.application.usecases.affiliate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.ports.in.affiliate.CreateAffiliateUseCase;
import com.credits.coopCredit.domain.ports.out.AffiliateRepositoryPort;
import java.time.LocalDate;

@Service
public class CreateAffiliateUseCaseImpl implements CreateAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepository;

    public CreateAffiliateUseCaseImpl(AffiliateRepositoryPort affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    @Transactional
    public Affiliate create(String documento, String nombre, Double salario, LocalDate fechaAfiliacion) {
        if (affiliateRepository.findByDocumento(documento).isPresent()) {
            throw new IllegalArgumentException("Ya existe un afiliado con ese documento");
        }

        if (salario == null || salario <= 0) {
            throw new IllegalArgumentException("El salario debe ser mayor a cero");
        }

        Affiliate affiliate = new Affiliate();
        affiliate.setDocumento(documento);
        affiliate.setNombre(nombre);
        affiliate.setSalario(salario);
        affiliate.setFechaAfiliacion(fechaAfiliacion != null ? fechaAfiliacion : LocalDate.now());
        affiliate.setEstado("ACTIVO");

        return affiliateRepository.save(affiliate);
    }
}
