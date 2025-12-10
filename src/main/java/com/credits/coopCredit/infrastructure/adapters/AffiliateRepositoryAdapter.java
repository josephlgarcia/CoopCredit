package com.credits.coopCredit.infrastructure.adapters;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.ports.out.AffiliateRepositoryPort;
import com.credits.coopCredit.infrastructure.entities.AffiliateEntity;
import com.credits.coopCredit.infrastructure.repositories.JpaAffiliateRepository;

@Component
public class AffiliateRepositoryAdapter implements AffiliateRepositoryPort {

    private final JpaAffiliateRepository repo;

    public AffiliateRepositoryAdapter(JpaAffiliateRepository repo) {
        this.repo = repo;
    }

    @Override
    public Affiliate save(Affiliate affiliate) {
        AffiliateEntity e = toEntity(affiliate);
        AffiliateEntity saved = repo.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<Affiliate> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Affiliate> findByDocumento(String documento) {
        return repo.findByDocumento(documento).map(this::toDomain);
    }

    private AffiliateEntity toEntity(Affiliate a) {
        if (a == null) return null;
        AffiliateEntity e = new AffiliateEntity();
        e.setId(a.getId());
        e.setDocumento(a.getDocumento());
        e.setNombre(a.getNombre());
        e.setSalario(a.getSalario());
        e.setFechaAfiliacion(a.getFechaAfiliacion());
        e.setEstado(a.getEstado());
        return e;
    }

    private Affiliate toDomain(AffiliateEntity e) {
        if (e == null) return null;
        Affiliate a = new Affiliate();
        a.setId(e.getId());
        a.setDocumento(e.getDocumento());
        a.setNombre(e.getNombre());
        a.setSalario(e.getSalario());
        a.setFechaAfiliacion(e.getFechaAfiliacion());
        a.setEstado(e.getEstado());
        return a;
    }
}
