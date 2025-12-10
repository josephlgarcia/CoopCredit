package com.credits.coopCredit.domain.ports.out;

/**
 * Puerto de salida para integración con servicio de riesgo externo.
 */
public interface RiskCentralPort {

    RiskEvaluationResponse evaluateRisk(String documento, Double monto, Integer plazo);

    class RiskEvaluationResponse {
        private final String documento;
        private final Integer score;
        private final String nivelRiesgo;
        private final String detalle;

        public RiskEvaluationResponse(String documento, Integer score, String nivelRiesgo, String detalle) {
            this.documento = documento;
            this.score = score;
            this.nivelRiesgo = nivelRiesgo;
            this.detalle = detalle;
        }

        public String getDocumento() {
            return documento;
        }

        public Integer getScore() {
            return score;
        }

        public String getNivelRiesgo() {
            return nivelRiesgo;
        }

        public String getDetalle() {
            return detalle;
        }
    }
}
