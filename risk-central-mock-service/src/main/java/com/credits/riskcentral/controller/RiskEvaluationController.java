package com.credits.riskcentral.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.credits.riskcentral.dto.RiskEvaluationRequest;
import com.credits.riskcentral.dto.RiskEvaluationResponse;
import com.credits.riskcentral.service.RiskEvaluationService;

@RestController
@RequestMapping("/risk-evaluation")
public class RiskEvaluationController {

    private final RiskEvaluationService service;

    public RiskEvaluationController(RiskEvaluationService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RiskEvaluationResponse> evaluate(@RequestBody RiskEvaluationRequest request) {
        RiskEvaluationResponse resp = service.evaluate(request);
        return ResponseEntity.ok(resp);
    }
}
