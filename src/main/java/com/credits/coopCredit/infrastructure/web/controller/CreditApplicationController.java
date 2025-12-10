package com.credits.coopCredit.infrastructure.web.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.credits.coopCredit.domain.model.CreditApplication;
import com.credits.coopCredit.domain.ports.in.credit.EvaluateCreditApplicationUseCase;
import com.credits.coopCredit.domain.ports.in.credit.GetCreditApplicationUseCase;
import com.credits.coopCredit.infrastructure.web.dto.credit.CreditApplicationDTO;
import com.credits.coopCredit.infrastructure.web.dto.credit.CreateCreditApplicationRequest;
import com.credits.coopCredit.infrastructure.web.mapper.CreditApplicationMapper;
import com.credits.coopCredit.application.usecases.credit.CreateCreditApplicationUseCaseImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Credit Application management.
 * Provides endpoints for creating, evaluating, and retrieving credit
 * applications.
 */
@RestController
@RequestMapping("/api/v1/credit-applications")
@Tag(name = "Credit Applications", description = "Gestión de Solicitudes de Crédito")
@SecurityRequirement(name = "bearerAuth")
public class CreditApplicationController {

    private final CreateCreditApplicationUseCaseImpl createUseCase;
    private final EvaluateCreditApplicationUseCase evaluateUseCase;
    private final GetCreditApplicationUseCase getUseCase;
    private final CreditApplicationMapper mapper;

    public CreditApplicationController(
            CreateCreditApplicationUseCaseImpl createUseCase,
            EvaluateCreditApplicationUseCase evaluateUseCase,
            GetCreditApplicationUseCase getUseCase,
            CreditApplicationMapper mapper) {
        this.createUseCase = createUseCase;
        this.evaluateUseCase = evaluateUseCase;
        this.getUseCase = getUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('AFILIADO', 'ADMIN')")
    @Operation(summary = "Crear solicitud de crédito", description = "Crea una nueva solicitud de crédito")
    public ResponseEntity<CreditApplicationDTO> create(
            @Valid @RequestBody CreateCreditApplicationRequest request,
            Authentication authentication) {

        // AFILIADO can only create applications for themselves
        // This should be validated in the use case based on authenticated user

        CreditApplication application = createUseCase.create(
                request.getDocumento(),
                request.getMonto(),
                request.getPlazo(),
                request.getTasa());

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(application));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('AFILIADO', 'ANALISTA', 'ADMIN')")
    @Operation(summary = "Obtener solicitud por ID", description = "Obtiene una solicitud de crédito por ID")
    public ResponseEntity<CreditApplicationDTO> getById(
            @PathVariable Long id,
            Authentication authentication) {

        // Role-based filtering should be applied in service/controller
        // AFILIADO: only their own, ANALISTA: PENDIENTE, ADMIN: all

        return getUseCase.getById(id)
                .map(application -> ResponseEntity.ok(mapper.toDTO(application)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('AFILIADO', 'ANALISTA', 'ADMIN')")
    @Operation(summary = "Listar solicitudes", description = "Lista solicitudes de crédito según rol y filtros")
    public ResponseEntity<List<CreditApplicationDTO>> list(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Long affiliateId,
            Authentication authentication) {

        List<CreditApplication> applications;

        if (estado != null && !estado.trim().isEmpty()) {
            applications = getUseCase.getByEstado(estado);
        } else if (affiliateId != null) {
            applications = getUseCase.getByAffiliate(affiliateId);
        } else {
            applications = getUseCase.getAll();
        }

        List<CreditApplicationDTO> dtos = applications.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{id}/evaluate")
    @PreAuthorize("hasAnyRole('ANALISTA', 'ADMIN')")
    @Operation(summary = "Evaluar solicitud", description = "Evalúa una solicitud de crédito PENDIENTE (ANALISTA, ADMIN)")
    public ResponseEntity<CreditApplicationDTO> evaluate(@PathVariable Long id) {
        CreditApplication evaluated = evaluateUseCase.evaluate(id);
        return ResponseEntity.ok(mapper.toDTO(evaluated));
    }
}
