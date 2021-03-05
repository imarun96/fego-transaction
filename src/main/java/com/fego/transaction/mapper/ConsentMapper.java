package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.dto.ConsentDataDto;
import com.fego.transaction.entity.Consent;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class ConsentMapper implements BaseMapper<Consent, ConsentDataDto> {

    public ConsentDataDto domainToDto(Consent consent) {
        ConsentDataDto consentDataDto = new ConsentDataDto();
        consentDataDto.setUserId(consent.getUserId());
        consentDataDto.setConsentId(consent.getConsentId());
        consentDataDto.setCustomerVua(consent.getCustomerVua());
        consentDataDto.setConsentStart(consent.getConsentStart());
        consentDataDto.setConsentExpiry(consent.getConsentExpiry());
        consentDataDto.setFiDataFrom(consent.getFiDataFrom());
        consentDataDto.setFiDataTo(consent.getFiDataTo());
        consentDataDto.setFrequencyUnit(consent.getFrequencyUnit());
        consentDataDto.setFrequencyValue(consent.getFrequencyValue());
        consentDataDto.setId(consent.getId());
        consentDataDto.setIsDeleted(consent.isDeleted());
        consentDataDto.setCreatedAt(consent.getCreatedAt());
        consentDataDto.setUpdatedAt(consent.getUpdatedAt());
        consentDataDto.setCreatedBy(consent.getCreatedBy());
        consentDataDto.setUpdatedBy(consent.getUpdatedBy());
        return consentDataDto;
    }

    public Consent dtoToDomain(ConsentDataDto consentDataDto) {
        Consent consent = new Consent();
        consent.setUserId(consentDataDto.getUserId());
        consent.setConsentId(consentDataDto.getConsentId());
        consent.setCustomerVua(consentDataDto.getCustomerVua());
        consent.setConsentStart(consentDataDto.getConsentStart());
        consent.setConsentExpiry(consentDataDto.getConsentExpiry());
        consent.setFiDataFrom(consentDataDto.getFiDataFrom());
        consent.setFiDataTo(consentDataDto.getFiDataTo());
        consent.setFrequencyUnit(consentDataDto.getFrequencyUnit());
        consent.setFrequencyValue(consentDataDto.getFrequencyValue());
        consent.setDeleted(consentDataDto.getIsDeleted());
        consent.setCreatedBy(consentDataDto.getCreatedBy());
        consent.setUpdatedBy(consentDataDto.getUpdatedBy());
        return consent;
    }
}