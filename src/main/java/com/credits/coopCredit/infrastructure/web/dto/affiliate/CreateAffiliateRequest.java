package com.credits.coopCredit.infrastructure.web.dto.affiliate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * DTO para crear nuevo afiliado.
 */
public class CreateAffiliateRequest {

    @NotBlank(message = "El documento es obligatorio")
    private String documento;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser mayor a cero")
    private Double salario;

    private LocalDate fechaAfiliacion;

    public CreateAffiliateRequest() {
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
}
