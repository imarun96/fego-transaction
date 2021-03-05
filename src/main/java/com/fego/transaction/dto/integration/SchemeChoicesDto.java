package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemeChoicesDto {

    @JsonProperty("SchemeChoice")
    private SchemeChoiceDto schemeChoice;

    public SchemeChoiceDto getSchemeChoice() {
        return schemeChoice;
    }

    public void setSchemeChoice(SchemeChoiceDto schemeChoice) {
        this.schemeChoice = schemeChoice;
    }
}