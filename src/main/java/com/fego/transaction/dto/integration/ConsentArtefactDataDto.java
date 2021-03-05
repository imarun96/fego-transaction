package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

public class ConsentArtefactDataDto {
    @JsonProperty("customerID")
    private String customerId;
    private String consentId;
    private String consentStart;
    private String consentExpiry;
    private String frequencyUnit;
    private int frequencyValue;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getConsentStart() {
        return consentStart;
    }

    public void setConsentStart(String consentStart) {
        this.consentStart = consentStart;
    }

    public String getConsentExpiry() {
        return consentExpiry;
    }

    public void setConsentExpiry(String consentExpiry) {
        this.consentExpiry = consentExpiry;
    }

    public String getFrequencyUnit() {
        return frequencyUnit;
    }

    public void setFrequencyUnit(String frequencyUnit) {
        this.frequencyUnit = frequencyUnit;
    }

    public int getFrequencyValue() {
        return frequencyValue;
    }

    public void setFrequencyValue(int frequencyValue) {
        this.frequencyValue = frequencyValue;
    }
}