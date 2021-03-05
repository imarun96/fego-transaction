package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HoldersDto {
    private String type;
    @JsonProperty("Holder")
    private HolderDto holder;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HolderDto getHolderDto() {
        return holder;
    }

    public void setHolderDto(HolderDto holder) {
        this.holder = holder;
    }
}