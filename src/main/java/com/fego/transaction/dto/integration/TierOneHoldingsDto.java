package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TierOneHoldingsDto {

    private String schemePreferenceType;
    private String freeUnits;
    private String investmentCost;
    private String investmentValue;
    @JsonProperty("Tier1Holding")
    private TierOneHoldingDto tierOneHoldingDto;

    public String getSchemePreferenceType() {
        return schemePreferenceType;
    }

    public void setSchemePreferenceType(String schemePreferenceType) {
        this.schemePreferenceType = schemePreferenceType;
    }

    public String getFreeUnits() {
        return freeUnits;
    }

    public void setFreeUnits(String freeUnits) {
        this.freeUnits = freeUnits;
    }

    public String getInvestmentCost() {
        return investmentCost;
    }

    public void setInvestmentCost(String investmentCost) {
        this.investmentCost = investmentCost;
    }

    public String getInvestmentValue() {
        return investmentValue;
    }

    public void setInvestmentValue(String investmentValue) {
        this.investmentValue = investmentValue;
    }

    public TierOneHoldingDto getTier1HoldingDto() {
        return tierOneHoldingDto;
    }

    public void setTier1HoldingDto(TierOneHoldingDto tierOneHoldingDto) {
        this.tierOneHoldingDto = tierOneHoldingDto;
    }
}