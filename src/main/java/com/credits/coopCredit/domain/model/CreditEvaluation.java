package com.credits.coopCredit.domain.model;

import java.time.LocalDate;

public class CreditEvaluation {
    private Long id;
    private Integer score;
    private String nivelRiesgo;
    private String detalle;
    private LocalDate fecha;
    private String decision; // APROBADO / RECHAZADO
    private String motivo;

    public CreditEvaluation() {}

    public CreditEvaluation(Long id, Integer score, String nivelRiesgo, String detalle, LocalDate fecha, String decision, String motivo) {
        this.id = id;
        this.score = score;
        this.nivelRiesgo = nivelRiesgo;
        this.detalle = detalle;
        this.fecha = fecha;
        this.decision = decision;
        this.motivo = motivo;
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
