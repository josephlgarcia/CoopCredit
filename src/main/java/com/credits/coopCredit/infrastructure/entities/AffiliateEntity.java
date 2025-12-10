package com.credits.coopCredit.infrastructure.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "affiliates", uniqueConstraints = {@UniqueConstraint(columnNames = {"documento"})})
public class AffiliateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double salario;

    private LocalDate fechaAfiliacion;

    private String estado;

    @OneToMany(mappedBy = "affiliate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditApplicationEntity> applications = new ArrayList<>();

    public AffiliateEntity() {}

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    public LocalDate getFechaAfiliacion() { return fechaAfiliacion; }
    public void setFechaAfiliacion(LocalDate fechaAfiliacion) { this.fechaAfiliacion = fechaAfiliacion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<CreditApplicationEntity> getApplications() { return applications; }
    public void setApplications(List<CreditApplicationEntity> applications) { this.applications = applications; }
}
