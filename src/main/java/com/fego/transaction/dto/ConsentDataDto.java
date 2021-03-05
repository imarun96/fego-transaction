package com.fego.transaction.dto;

import com.fego.transaction.common.base.BaseDto;

/**
 * @author Arun Balaji Rajasekaran
 */

public class ConsentDataDto extends BaseDto {
    private long userId;
    private String consentId;
    private String customerVua;
    private String consentStart;
    private String consentExpiry;
    private String fiDataFrom;
    private String fiDataTo;
    private String frequencyUnit;
    private Integer frequencyValue;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }

    public String getCustomerVua() {
        return customerVua;
    }

    public void setCustomerVua(String customerVua) {
        this.customerVua = customerVua;
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

    public String getFiDataFrom() {
        return fiDataFrom;
    }

    public void setFiDataFrom(String fiDataFrom) {
        this.fiDataFrom = fiDataFrom;
    }

    public String getFiDataTo() {
        return fiDataTo;
    }

    public void setFiDataTo(String fiDataTo) {
        this.fiDataTo = fiDataTo;
    }

    public String getFrequencyUnit() {
        return frequencyUnit;
    }

    public void setFrequencyUnit(String frequencyUnit) {
        this.frequencyUnit = frequencyUnit;
    }

    public Integer getFrequencyValue() {
        return frequencyValue;
    }

    public void setFrequencyValue(Integer frequencyValue) {
        this.frequencyValue = frequencyValue;
    }
}