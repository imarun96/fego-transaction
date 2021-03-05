package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TierTwoSchemeTransactionsDto {

    @JsonProperty("Tier2SchemeTransaction")
    private TierTwoSchemeTransactionDto tierTwoSchemeTransactionDto;

    public TierTwoSchemeTransactionDto getTier2SchemeTransactionDto() {
        return tierTwoSchemeTransactionDto;
    }

    public void setTier2SchemeTransactionDto(TierTwoSchemeTransactionDto tierTwoSchemeTransactionDto) {
        this.tierTwoSchemeTransactionDto = tierTwoSchemeTransactionDto;
    }
}