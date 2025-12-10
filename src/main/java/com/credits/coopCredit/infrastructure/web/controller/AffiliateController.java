package com.credits.coopCredit.infrastructure.web.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.domain.ports.in.affiliate.CreateAffiliateUseCase;
import com.credits.coopCredit.domain.ports.in.affiliate.GetAffiliateUseCase;
import com.credits.coopCredit.domain.ports.in.affiliate.UpdateAffiliateUseCase;
import com.credits.coopCredit.infrastructure.web.dto.affiliate.AffiliateDTO;
import com.credits.coopCredit.infrastructure.web.dto.affiliate.CreateAffiliateRequest;
import com.credits.coopCredit.infrastructure.web.dto.affiliate.UpdateAffiliateRequest;
import com.credits.coopCredit.infrastructure.web.mapper.AffiliateMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST Controller for Affiliate management.
 * Provides endpoints for CRUD operations on affiliates.
 */
@RestController
@RequestMapping("/api/v1/affiliates")
@Tag(name = "Affiliates", description = "Gestión de Afiliados")
@SecurityRequirement(name = "bearerAuth")
public class AffiliateController {
    
    private final CreateAffiliateUseCase createUseCase;
    private final UpdateAffiliateUseCase updateUseCase;
    private final GetAffiliateUseCase getUseCase;
    private final AffiliateMapper mapper;
    
    public AffiliateController(
            CreateAffiliateUseCase createUseCase,
            UpdateAffiliateUseCase updateUseCase,
            GetAffiliateUseCase getUseCase,
            AffiliateMapper mapper) {
        this.createUseCase = createUseCase;
        this.updateUseCase = updateUseCase;
        this.getUseCase = getUseCase;
        this.mapper = mapper;
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear afiliado", description = "Crea un nuevo afiliado (solo ADMIN)")
    public ResponseEntity<AffiliateDTO> create(@Valid @RequestBody CreateAffiliateRequest request) {
        Affiliate affiliate = createUseCase.create(
            request.getDocumento(),
            request.getNombre(),
            request.getSalario(),
            request.getFechaAfiliacion()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(affiliate));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar afiliado", description = "Actualiza información de un afiliado (solo ADMIN)")
    public ResponseEntity<AffiliateDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateAffiliateRequest request) {
        Affiliate affiliate = updateUseCase.update(
            id,
            request.getNombre(),
            request.getSalario(),
            request.getEstado()
        );
        return ResponseEntity.ok(mapper.toDTO(affiliate));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    @Operation(summary = "Obtener afiliado por ID", description = "Obtiene un afiliado por ID (ADMIN, ANALISTA)")
    public ResponseEntity<AffiliateDTO> getById(@PathVariable Long id) {
        return getUseCase.getById(id)
            .map(affiliate -> ResponseEntity.ok(mapper.toDTO(affiliate)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/documento/{documento}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA', 'AFILIADO')")
    @Operation(summary = "Obtener afiliado por documento", description = "Obtiene un afiliado por número de documento")
    public ResponseEntity<AffiliateDTO> getByDocumento(@PathVariable String documento) {
        return getUseCase.getByDocumento(documento)
            .map(affiliate -> ResponseEntity.ok(mapper.toDTO(affiliate)))
            .orElse(ResponseEntity.notFound().build());
    }
}
