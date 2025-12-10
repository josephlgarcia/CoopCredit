package com.credits.coopCredit.infrastructure.adapters;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.credits.coopCredit.domain.model.CreditApplication;
import com.credits.coopCredit.domain.model.CreditEvaluation;
import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.ports.out.CreditApplicationRepositoryPort;
import com.credits.coopCredit.infrastructure.entities.CreditApplicationEntity;
import com.credits.coopCredit.infrastructure.entities.CreditEvaluationEntity;
import com.credits.coopCredit.infrastructure.entities.AffiliateEntity;
import com.credits.coopCredit.infrastructure.repositories.JpaCreditApplicationRepository;

@Component
public class CreditApplicationRepositoryAdapter implements CreditApplicationRepositoryPort {

    private final JpaCreditApplicationRepository repo;

    public CreditApplicationRepositoryAdapter(JpaCreditApplicationRepository repo) {
        this.repo = repo;
    }

    @Override
    public CreditApplication save(CreditApplication application) {
        CreditApplicationEntity e = toEntity(application);
        CreditApplicationEntity saved = repo.save(e);
        return toDomain(saved);
    }

    @Override
    public Optional<CreditApplication> findById(Long id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public List<CreditApplication> findAll() {
        return repo.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditApplication> findByEstado(String estado) {
        return repo.findByEstado(estado).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<CreditApplication> findByAffiliateId(Long affiliateId) {
        return repo.findByAffiliateId(affiliateId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private CreditApplicationEntity toEntity(CreditApplication a) {
        if (a == null)
            return null;
        CreditApplicationEntity e = new CreditApplicationEntity();
        e.setId(a.getId());
        if (a.getAffiliate() != null) {
            AffiliateEntity ae = new AffiliateEntity();
            ae.setId(a.getAffiliate().getId());
            ae.setDocumento(a.getAffiliate().getDocumento());
            ae.setNombre(a.getAffiliate().getNombre());
            ae.setSalario(a.getAffiliate().getSalario());
            ae.setFechaAfiliacion(a.getAffiliate().getFechaAfiliacion());
            ae.setEstado(a.getAffiliate().getEstado());
            e.setAffiliate(ae);
        }
        e.setMonto(a.getMonto());
        e.setPlazo(a.getPlazo());
        e.setTasa(a.getTasa());
        e.setFechaSolicitud(a.getFechaSolicitud());
        e.setEstado(a.getEstado());
        if (a.getEvaluacion() != null) {
            CreditEvaluationEntity ce = new CreditEvaluationEntity();
            ce.setId(a.getEvaluacion().getId());
            ce.setScore(a.getEvaluacion().getScore());
            ce.setNivelRiesgo(a.getEvaluacion().getNivelRiesgo());
            ce.setDetalle(a.getEvaluacion().getDetalle());
            ce.setFecha(a.getEvaluacion().getFecha());
            ce.setDecision(a.getEvaluacion().getDecision());
            ce.setMotivo(a.getEvaluacion().getMotivo());
            e.setEvaluacion(ce);
        }
        return e;
    }

    private CreditApplication toDomain(CreditApplicationEntity e) {
        if (e == null)
            return null;
        CreditApplication a = new CreditApplication();
        a.setId(e.getId());
        if (e.getAffiliate() != null) {
            Affiliate af = new Affiliate();
            af.setId(e.getAffiliate().getId());
            af.setDocumento(e.getAffiliate().getDocumento());
            af.setNombre(e.getAffiliate().getNombre());
            af.setSalario(e.getAffiliate().getSalario());
            af.setFechaAfiliacion(e.getAffiliate().getFechaAfiliacion());
            af.setEstado(e.getAffiliate().getEstado());
            a.setAffiliate(af);
        }
        a.setMonto(e.getMonto());
        a.setPlazo(e.getPlazo());
        a.setTasa(e.getTasa());
        a.setFechaSolicitud(e.getFechaSolicitud());
        a.setEstado(e.getEstado());
        if (e.getEvaluacion() != null) {
            CreditEvaluation ce = new CreditEvaluation();
            ce.setId(e.getEvaluacion().getId());
            ce.setScore(e.getEvaluacion().getScore());
            ce.setNivelRiesgo(e.getEvaluacion().getNivelRiesgo());
            ce.setDetalle(e.getEvaluacion().getDetalle());
            ce.setFecha(e.getEvaluacion().getFecha());
            ce.setDecision(e.getEvaluacion().getDecision());
            ce.setMotivo(e.getEvaluacion().getMotivo());
            a.setEvaluacion(ce);
        }
        return a;
    }
}
