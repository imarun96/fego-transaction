package com.fego.transaction.dto;

/**
 * @author Arun Balaji Rajasekaran
 */

public class TransactionSyncDto {
    private String consentId;

    public TransactionSyncDto(String consentId) {
        this.consentId = consentId;
    }

    public String getConsentId() {
        return consentId;
    }

    public void setConsentId(String consentId) {
        this.consentId = consentId;
    }
}