package com.credits.coopCredit.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "credit_applications")
public class CreditApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliate_id")
    private AffiliateEntity affiliate;

    private Double monto;
    private Integer plazo;
    private Double tasa;
    private LocalDate fechaSolicitud;
    private String estado;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private CreditEvaluationEntity evaluacion;

    public CreditApplicationEntity() {}

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AffiliateEntity getAffiliate() { return affiliate; }
    public void setAffiliate(AffiliateEntity affiliate) { this.affiliate = affiliate; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public Integer getPlazo() { return plazo; }
    public void setPlazo(Integer plazo) { this.plazo = plazo; }
    public Double getTasa() { return tasa; }
    public void setTasa(Double tasa) { this.tasa = tasa; }
    public LocalDate getFechaSolicitud() { return fechaSolicitud; }
    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public CreditEvaluationEntity getEvaluacion() { return evaluacion; }
    public void setEvaluacion(CreditEvaluationEntity evaluacion) { this.evaluacion = evaluacion; }
}
