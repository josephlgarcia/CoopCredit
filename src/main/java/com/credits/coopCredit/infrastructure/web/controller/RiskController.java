package com.credits.coopCredit.infrastructure.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


/**
 * Mock ligero de central de riesgo. Respuesta determinista por documento.
 */
@RestController
public class RiskController {

    @PostMapping("/risk-evaluation")
    public ResponseEntity<RiskResponse> evaluate(@RequestBody RiskRequest req) {
        String doc = Objects.toString(req.documento, "");
        int seed = Math.abs(doc.hashCode()) % 1000;
        int score = 300 + (seed % 651); // 300..950
        String nivel;
        String detalle;
        if (score <= 500) {
            nivel = "ALTO";
            detalle = "Historial crediticio alto riesgo.";
        } else if (score <= 700) {
            nivel = "MEDIO";
            detalle = "Historial crediticio moderado.";
        } else {
            nivel = "BAJO";
            detalle = "Historial crediticio bajo riesgo.";
        }

        RiskResponse r = new RiskResponse();
        r.documento = req.documento;
        r.score = score;
        r.nivelRiesgo = nivel;
        r.detalle = detalle;
        return ResponseEntity.ok(r);
    }

    public static class RiskRequest {
        public String documento;
        public Double monto;
        public Integer plazo;
    }

    public static class RiskResponse {
        public String documento;
        public Integer score;
        public String nivelRiesgo;
        public String detalle;
    }
}
