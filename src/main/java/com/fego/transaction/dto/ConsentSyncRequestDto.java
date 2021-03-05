package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fego.transaction.dto.integration.FiDataRangeDto;

/**
 * @author Arun Balaji Rajasekaran
 */

public class ConsentSyncRequestDto {

    private String consentId;
    @JsonProperty("FIDataRange")
    private FiDataRangeDto fiDataRangeDto;

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public FiDataRangeDto getFiDataRangeDto() {
        return fiDataRangeDto;
    }

    public void setFiDataRangeDto(FiDataRangeDto fiDataRangeDto) {
        this.fiDataRangeDto = fiDataRangeDto;
    }
}