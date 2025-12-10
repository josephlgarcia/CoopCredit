package com.credits.riskcentral.dto;

public class RiskEvaluationResponse {
    private String documento;
    private Integer score;
    private String nivelRiesgo;
    private String detalle;

    public RiskEvaluationResponse() {}

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getNivelRiesgo() { return nivelRiesgo; }
    public void setNivelRiesgo(String nivelRiesgo) { this.nivelRiesgo = nivelRiesgo; }
    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
}
