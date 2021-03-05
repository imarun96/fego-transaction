package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HoldingsDto {

    @JsonProperty("Holding")
    private HoldingDto holdingDto;
    @JsonProperty("Tier1Holdings")
    private TierOneHoldingsDto tierOneHoldingsDto;
    @JsonProperty("Tier2Holdings")
    private TierTwoHoldingsDto tierTwoHoldingsDto;

    public TierOneHoldingsDto getTier1HoldingsDto() {
        return tierOneHoldingsDto;
    }

    public void setTier1HoldingsDto(TierOneHoldingsDto tierOneHoldingsDto) {
        this.tierOneHoldingsDto = tierOneHoldingsDto;
    }

    public TierTwoHoldingsDto getTier2HoldingsDto() {
        return tierTwoHoldingsDto;
    }

    public void setTier2HoldingsDto(TierTwoHoldingsDto tierTwoHoldingsDto) {
        this.tierTwoHoldingsDto = tierTwoHoldingsDto;
    }

    public HoldingDto getHoldingDto() {
        return holdingDto;
    }

    public void setHoldingDto(HoldingDto holdingDto) {
        this.holdingDto = holdingDto;
    }
}