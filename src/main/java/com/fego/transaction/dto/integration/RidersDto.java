package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RidersDto {

    private RiderDto riderDto;

    public RiderDto getRiderDto() {
        return riderDto;
    }

    public void setRiderDto(RiderDto riderDto) {
        this.riderDto = riderDto;
    }
}