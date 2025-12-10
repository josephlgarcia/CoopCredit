package com.credits.coopCredit.infrastructure.web.dto.credit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para crear solicitud de crédito.
 */
public class CreateCreditApplicationRequest {

    @NotBlank(message = "El documento del afiliado es obligatorio")
    private String documento;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a cero")
    private Double monto;

    @NotNull(message = "El plazo es obligatorio")
    @Positive(message = "El plazo debe ser mayor a cero")
    private Integer plazo;

    @NotNull(message = "La tasa es obligatoria")
    @Positive(message = "La tasa debe ser mayor a cero")
    private Double tasa;

    public CreateCreditApplicationRequest() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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
}
