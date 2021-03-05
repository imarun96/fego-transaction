package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TierOneSchemeTransactionsDto {

    @JsonProperty("Tier1SchemeTransaction")
    private TierOneSchemeTransactionDto tierOneSchemeTransactionDto;

    public TierOneSchemeTransactionDto getTier1SchemeTransactionDto() {
        return tierOneSchemeTransactionDto;
    }

    public void setTier1SchemeTransactionDto(TierOneSchemeTransactionDto tierOneSchemeTransactionDto) {
        this.tierOneSchemeTransactionDto = tierOneSchemeTransactionDto;
    }
}