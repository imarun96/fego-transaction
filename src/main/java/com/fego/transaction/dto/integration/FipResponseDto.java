package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class FipResponseDto {
    @JsonProperty("fipList")
    private List<FipDto> fip;
    private Boolean status;

    public List<FipDto> getFip() {
        return fip;
    }

    public void setFip(List<FipDto> fip) {
        this.fip = fip;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}