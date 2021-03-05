package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {

    @JsonProperty("Holders")
    private HoldersDto holdersDto;
    @JsonProperty("Riders")
    private RidersDto ridersDto;

    public HoldersDto getHoldersDto() {
        return holdersDto;
    }

    public void setHoldersDto(HoldersDto holdersDto) {
        this.holdersDto = holdersDto;
    }

    public RidersDto getRidersDto() {
        return ridersDto;
    }

    public void setRidersDto(RidersDto ridersDto) {
        this.ridersDto = ridersDto;
    }
}