package com.credits.coopCredit.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.credits.coopCredit.domain.model.CreditApplication;
import com.credits.coopCredit.domain.model.CreditEvaluation;
import com.credits.coopCredit.infrastructure.web.dto.credit.CreditApplicationDTO;
import com.credits.coopCredit.infrastructure.web.dto.credit.CreditApplicationDTO.CreditEvaluationDTO;

/**
 * MapStruct mapper para CreditApplication.
 */
@Mapper(componentModel = "spring")
public interface CreditApplicationMapper {

    @Mapping(source = "affiliate.id", target = "affiliateId")
    @Mapping(source = "affiliate.nombre", target = "affiliateNombre")
    @Mapping(source = "affiliate.documento", target = "affiliateDocumento")
    CreditApplicationDTO toDTO(CreditApplication application);

    CreditEvaluationDTO evaluationToDTO(CreditEvaluation evaluation);
}
