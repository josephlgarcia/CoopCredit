package com.credits.coopCredit.infrastructure.web.dto.affiliate;

import jakarta.validation.constraints.Positive;

/**
 * DTO para actualizar afiliado.
 */
public class UpdateAffiliateRequest {

    private String nombre;

    @Positive(message = "El salario debe ser mayor a cero")
    private Double salario;

    private String estado;

    public UpdateAffiliateRequest() {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
