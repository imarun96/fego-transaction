package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoversDto {

    @JsonProperty("Cover")
    private CoverDto cover;

    public CoverDto getCover() {
        return cover;
    }

    public void setCover(CoverDto cover) {
        this.cover = cover;
    }
}