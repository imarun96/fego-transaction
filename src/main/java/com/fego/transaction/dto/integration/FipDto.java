package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class FipDto {

    @JsonProperty("fipID")
    private String fipId;
    private String code;
    private String fipName;
    private String logoUrl;
    private String smallUrl;
    @JsonProperty("discoverOTP")
    private Boolean discoverOtp;
    @JsonProperty("FIs")
    private List<String> fis;
    private List<IdentifiersDto> identifiers;

    public String getFipId() {
        return fipId;
    }

    public void setFipId(String fipId) {
        this.fipId = fipId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFipName() {
        return fipName;
    }

    public void setFipName(String fipName) {
        this.fipName = fipName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public Boolean getDiscoverOtp() {
        return discoverOtp;
    }

    public void setDiscoverOtp(Boolean discoverOtp) {
        this.discoverOtp = discoverOtp;
    }

    public List<String> getFis() {
        return fis;
    }

    public void setFis(List<String> fis) {
        this.fis = fis;
    }

    public List<IdentifiersDto> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<IdentifiersDto> identifiers) {
        this.identifiers = identifiers;
    }
}