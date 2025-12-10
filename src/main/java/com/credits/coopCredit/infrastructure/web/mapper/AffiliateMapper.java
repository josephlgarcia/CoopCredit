package com.credits.coopCredit.infrastructure.web.mapper;

import org.mapstruct.Mapper;

import com.credits.coopCredit.domain.model.Affiliate;
import com.credits.coopCredit.infrastructure.web.dto.affiliate.AffiliateDTO;

/**
 * MapStruct mapper for Affiliate.
 */
@Mapper(componentModel = "spring")
public interface AffiliateMapper {

    AffiliateDTO toDTO(Affiliate affiliate);
}
