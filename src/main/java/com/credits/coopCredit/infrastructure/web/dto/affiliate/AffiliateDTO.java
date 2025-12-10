package com.credits.coopCredit.infrastructure.web.dto.affiliate;

import java.time.LocalDate;

/**
 * DTO para respuesta de afiliado.
 */
public class AffiliateDTO {

    private Long id;
    private String documento;
    private String nombre;
    private Double salario;
    private LocalDate fechaAfiliacion;
    private String estado;

    public AffiliateDTO() {
    }

    public AffiliateDTO(Long id, String documento, String nombre, Double salario, LocalDate fechaAfiliacion,
            String estado) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.salario = salario;
        this.fechaAfiliacion = fechaAfiliacion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public LocalDate getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(LocalDate fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
