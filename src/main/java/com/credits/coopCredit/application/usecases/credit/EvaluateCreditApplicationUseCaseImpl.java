package com.credits.coopCredit.application.usecases.credit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.credits.coopCredit.domain.model.CreditApplication;
import com.credits.coopCredit.domain.model.CreditEvaluation;
import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.ports.in.credit.EvaluateCreditApplicationUseCase;
import com.credits.coopCredit.domain.ports.out.CreditApplicationRepositoryPort;
import com.credits.coopCredit.domain.ports.out.CreditEvaluationRepositoryPort;
import com.credits.coopCredit.domain.ports.out.RiskCentralPort;
import java.time.LocalDate;

@Service
public class EvaluateCreditApplicationUseCaseImpl implements EvaluateCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort applicationRepository;
    private final CreditEvaluationRepositoryPort evaluationRepository;
    private final RiskCentralPort riskCentralPort;

    public EvaluateCreditApplicationUseCaseImpl(
            CreditApplicationRepositoryPort applicationRepos,
            CreditEvaluationRepositoryPort evaluationRepository,
            RiskCentralPort riskCentralPort) {
        this.applicationRepository = applicationRepos;
        this.evaluationRepository = evaluationRepository;
        this.riskCentralPort = riskCentralPort;
    }

    @Transactional
    public CreditApplication evaluate(Long id) {
        CreditApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        if (!"PENDIENTE".equalsIgnoreCase(application.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden evaluar solicitudes PENDIENTE");
        }

        Affiliate affiliate = application.getAffiliate();
        RiskCentralPort.RiskEvaluationResponse riskResponse = riskCentralPort.evaluateRisk(
                affiliate.getDocumento(),
                application.getMonto(),
                application.getPlazo());

        CreditEvaluation evaluation = new CreditEvaluation();
        evaluation.setScore(riskResponse.getScore());
        evaluation.setNivelRiesgo(riskResponse.getNivelRiesgo());
        evaluation.setDetalle(riskResponse.getDetalle());
        evaluation.setFecha(LocalDate.now());

        double cuotaMensual = application.getMonto() / application.getPlazo();
        double relacionCuotaIngreso = cuotaMensual / affiliate.getSalario();

        if (relacionCuotaIngreso > 0.4) {
            evaluation.setDecision("RECHAZADO");
            evaluation.setMotivo("La cuota mensual excede el 40% del ingreso");
        } else if (application.getMonto() > affiliate.getSalario() * 20) {
            evaluation.setDecision("RECHAZADO");
            evaluation.setMotivo("Monto solicitado excede el límite permitido");
        } else if (riskResponse.getScore() < 501) {
            evaluation.setDecision("RECHAZADO");
            evaluation.setMotivo("Score de riesgo insuficiente");
        } else {
            evaluation.setDecision("APROBADO");
            evaluation.setMotivo("La solicitud cumple con todas las políticas de crédito");
        }

        evaluation = evaluationRepository.save(evaluation);

        application.setEvaluacion(evaluation);
        application.setEstado(evaluation.getDecision());

        return applicationRepository.save(application);
    }
}
