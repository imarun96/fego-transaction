package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchemeChoiceDto {

    private String allocationPercent;
    private String pfmId;
    private String pfmName;
    private String schemeId;
    private String schemeName;

    public String getAllocationPercent() {
        return allocationPercent;
    }

    public void setAllocationPercent(String allocationPercent) {
        this.allocationPercent = allocationPercent;
    }

    public String getPfmId() {
        return pfmId;
    }

    public void setPfmId(String pfmId) {
        this.pfmId = pfmId;
    }

    public String getPfmName() {
        return pfmName;
    }

    public void setPfmName(String pfmName) {
        this.pfmName = pfmName;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }
}