package com.credits.coopCredit.infrastructure.adapters.out;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.credits.coopCredit.domain.ports.out.RiskCentralPort;
import java.util.HashMap;
import java.util.Map;

@Component
public class RiskCentralAdapter implements RiskCentralPort {

    private final RestTemplate restTemplate;
    private final String riskServiceUrl;

    public RiskCentralAdapter(
            RestTemplate restTemplate,
            @Value("${risk.service.url:http://localhost:8081}") String riskServiceUrl) {
        this.restTemplate = restTemplate;
        this.riskServiceUrl = riskServiceUrl;
    }

    @Override
    public RiskEvaluationResponse evaluateRisk(String documento, Double monto, Integer plazo) {
        Map<String, Object> request = new HashMap<>();
        request.put("documento", documento);
        request.put("monto", monto);
        request.put("plazo", plazo);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(
                    riskServiceUrl + "/risk-evaluation",
                    request,
                    Map.class);

            if (response == null) {
                throw new RuntimeException("Servicio de riesgo no disponible");
            }

            Integer score = extractInteger(response.get("score"));
            String nivelRiesgo = String.valueOf(response.get("nivelRiesgo"));
            String detalle = String.valueOf(response.get("detalle"));
            String documentoResp = String.valueOf(response.get("documento"));

            return new RiskEvaluationResponse(documentoResp, score, nivelRiesgo, detalle);

        } catch (Exception e) {
            throw new RuntimeException("Error al evaluar riesgo crediticio: " + e.getMessage(), e);
        }
    }

    private Integer extractInteger(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        return Integer.parseInt(String.valueOf(value));
    }
}
