package com.credits.coopCredit.domain.model;

import java.time.LocalDate;

public class CreditApplication {
    private Long id;
    private Affiliate affiliate;
    private Double monto;
    private Integer plazo; // meses
    private Double tasa;
    private LocalDate fechaSolicitud;
    private String estado; // PENDIENTE, APROBADO, RECHAZADO
    private CreditEvaluation evaluacion;

    public CreditApplication() {}

    public CreditApplication(Long id, Affiliate affiliate, Double monto, Integer plazo, Double tasa, LocalDate fechaSolicitud, String estado) {
        this.id = id;
        this.affiliate = affiliate;
        this.monto = monto;
        this.plazo = plazo;
        this.tasa = tasa;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Affiliate getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(Affiliate affiliate) {
        this.affiliate = affiliate;
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

    public CreditEvaluation getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(CreditEvaluation evaluacion) {
        this.evaluacion = evaluacion;
    }
}
