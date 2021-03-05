package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

public class AccountLinkRequestDto {
    @JsonProperty("accRefNumber")
    private String accountReferenceNumber;
    @JsonProperty("linkRefNumber")
    private String linkReferenceNumber;

    public String getAccountReferenceNumber() {
        return accountReferenceNumber;
    }

    public void setAccountReferenceNumber(String accountReferenceNumber) {
        this.accountReferenceNumber = accountReferenceNumber;
    }

    public String getLinkReferenceNumber() {
        return linkReferenceNumber;
    }

    public void setLinkReferenceNumber(String linkReferenceNumber) {
        this.linkReferenceNumber = linkReferenceNumber;
    }
}