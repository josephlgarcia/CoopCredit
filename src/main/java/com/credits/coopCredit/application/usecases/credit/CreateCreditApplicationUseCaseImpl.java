package com.credits.coopCredit.application.usecases.credit;

import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.model.CreditApplication;
import com.credits.coopCredit.domain.model.CreditEvaluation;
import com.credits.coopCredit.domain.ports.out.AffiliateRepositoryPort;
import com.credits.coopCredit.domain.ports.out.CreditApplicationRepositoryPort;
import com.credits.coopCredit.domain.ports.out.CreditEvaluationRepositoryPort;
import com.credits.coopCredit.domain.ports.out.RiskCentralPort;

@Service
public class CreateCreditApplicationUseCaseImpl {

    private final AffiliateRepositoryPort affiliateRepo;
    private final CreditApplicationRepositoryPort applicationRepo;
    private final CreditEvaluationRepositoryPort evaluationRepo;
    private final RiskCentralPort riskCentralPort;

    public CreateCreditApplicationUseCaseImpl(
            AffiliateRepositoryPort affiliateRepo,
            CreditApplicationRepositoryPort applicationRepo,
            CreditEvaluationRepositoryPort evaluationRepo,
            RiskCentralPort riskCentralPort) {
        this.affiliateRepo = affiliateRepo;
        this.applicationRepo = applicationRepo;
        this.evaluationRepo = evaluationRepo;
        this.riskCentralPort = riskCentralPort;
    }

    @Transactional
    public CreditApplication create(String documento, Double monto, Integer plazo, Double tasa) {
        Affiliate affiliate = affiliateRepo.findByDocumento(documento)
                .orElseThrow(() -> new IllegalArgumentException("Afiliado no encontrado"));

        if (!"ACTIVO".equalsIgnoreCase(affiliate.getEstado())) {
            throw new IllegalArgumentException("Afiliado no activo");
        }

        if (affiliate.getSalario() == null || affiliate.getSalario() <= 0) {
            throw new IllegalArgumentException("Salario inválido");
        }

        if (affiliate.getFechaAfiliacion() == null
                || affiliate.getFechaAfiliacion().plusMonths(6).isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Antigüedad mínima de 6 meses requerida");
        }

        CreditApplication app = new CreditApplication();
        app.setAffiliate(affiliate);
        app.setMonto(monto);
        app.setPlazo(plazo);
        app.setTasa(tasa);
        app.setFechaSolicitud(LocalDate.now());
        app.setEstado("PENDIENTE");

        app = applicationRepo.save(app);

        RiskCentralPort.RiskEvaluationResponse riskResponse = riskCentralPort.evaluateRisk(
                documento,
                monto,
                plazo);

        int score = riskResponse.getScore();
        String nivel = riskResponse.getNivelRiesgo();
        String detalle = riskResponse.getDetalle();

        CreditEvaluation eval = new CreditEvaluation();
        eval.setScore(score);
        eval.setNivelRiesgo(nivel);
        eval.setDetalle(detalle);
        eval.setFecha(LocalDate.now());

        double cuota = monto / plazo;
        if (cuota > affiliate.getSalario() * 0.4) {
            eval.setDecision("RECHAZADO");
            eval.setMotivo("Cuota/ingreso insuficiente");
        } else if (monto > affiliate.getSalario() * 20) {
            eval.setDecision("RECHAZADO");
            eval.setMotivo("Monto superior al permitido por salario");
        } else if (score < 501) {
            eval.setDecision("RECHAZADO");
            eval.setMotivo("Score de riesgo alto");
        } else {
            eval.setDecision("APROBADO");
            eval.setMotivo("Cumple políticas internas");
        }

        evaluationRepo.save(eval);

        app.setEvaluacion(eval);
        app.setEstado(eval.getDecision());
        app = applicationRepo.save(app);

        return app;
    }
}
