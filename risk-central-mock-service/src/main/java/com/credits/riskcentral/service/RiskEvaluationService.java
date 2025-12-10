package com.credits.riskcentral.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.credits.riskcentral.dto.RiskEvaluationRequest;
import com.credits.riskcentral.dto.RiskEvaluationResponse;

@Service
public class RiskEvaluationService {

    public RiskEvaluationResponse evaluate(RiskEvaluationRequest request) {
        String documento = Objects.toString(request.getDocumento(), "");
        int seed = Math.abs(documento.hashCode()) % 1000; // 0..999
        int score = 300 + (seed % 651); // 300..950

        String nivel;
        String detalle;
        if (score <= 500) {
            nivel = "ALTO";
            detalle = "Historial crediticio con alto riesgo.";
        } else if (score <= 700) {
            nivel = "MEDIO";
            detalle = "Historial crediticio moderado.";
        } else {
            nivel = "BAJO";
            detalle = "Historial crediticio con bajo riesgo.";
        }

        RiskEvaluationResponse resp = new RiskEvaluationResponse();
        resp.setDocumento(documento);
        resp.setScore(score);
        resp.setNivelRiesgo(nivel);
        resp.setDetalle(detalle);
        return resp;
    }
}
