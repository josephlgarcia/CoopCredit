package com.credits.coopCredit.infrastructure.web.dto.credit;

import java.time.LocalDate;

/**
 * DTO para respuesta de solicitud de crédito.
 */
public class CreditApplicationDTO {

    private Long id;
    private Long affiliateId;
    private String affiliateNombre;
    private String affiliateDocumento;
    private Double monto;
    private Integer plazo;
    private Double tasa;
    private LocalDate fechaSolicitud;
    private String estado;
    private CreditEvaluationDTO evaluacion;

    public CreditApplicationDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(Long affiliateId) {
        this.affiliateId = affiliateId;
    }

    public String getAffiliateNombre() {
        return affiliateNombre;
    }

    public void setAffiliateNombre(String affiliateNombre) {
        this.affiliateNombre = affiliateNombre;
    }

    public String getAffiliateDocumento() {
        return affiliateDocumento;
    }

    public void setAffiliateDocumento(String affiliateDocumento) {
        this.affiliateDocumento = affiliateDocumento;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public Double getTasa() {
        return tasa;
    }

    public void setTasa(Double tasa) {
        this.tasa = tasa;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CreditEvaluationDTO getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(CreditEvaluationDTO evaluacion) {
        this.evaluacion = evaluacion;
    }

    // Inner class for evaluation
    public static class CreditEvaluationDTO {
        private Long id;
        private Integer score;
        private String nivelRiesgo;
        private String detalle;
        private LocalDate fecha;
        private String decision;
        private String motivo;

        public CreditEvaluationDTO() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getNivelRiesgo() {
            return nivelRiesgo;
        }

        public void setNivelRiesgo(String nivelRiesgo) {
            this.nivelRiesgo = nivelRiesgo;
        }

        public String getDetalle() {
            return detalle;
        }

        public void setDetalle(String detalle) {
            this.detalle = detalle;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }

        public String getDecision() {
            return decision;
        }

        public void setDecision(String decision) {
            this.decision = decision;
        }

        public String getMotivo() {
            return motivo;
        }

        public void setMotivo(String motivo) {
            this.motivo = motivo;
        }
    }
}
