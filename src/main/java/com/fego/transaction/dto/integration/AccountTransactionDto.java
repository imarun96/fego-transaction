package com.fego.transaction.dto.integration;

/**
 * @author Arun Balaji Rajasekaran
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fego.transaction.dto.TransactionsDto;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTransactionDto {

    @JsonProperty("linkedAccRef")
    private String linkedAccountReference;
    @JsonProperty("maskedAccNumber")
    private String maskedAccountNumber;
    private String xmlns;
    @JsonProperty("xmlns:xsi")
    @JsonIgnore
    private String xsi;
    @JsonProperty("xsi:schemaLocation")
    @JsonIgnore
    private String schemaLocation;
    private String type;
    private String version;
    @JsonProperty("Profile")
    private ProfileDto profile;
    @JsonProperty("Summary")
    private SummaryDto summary;
    @JsonProperty("Transactions")
    private TransactionsDto transactions;

    public AccountTransactionDto(String linkedAccountReference, String maskedAccountNumber, String xmlns, String xsi, String schemaLocation,
                                 String type, String version, ProfileDto profile, SummaryDto summary, TransactionsDto transactions) {
        super();
        this.linkedAccountReference = linkedAccountReference;
        this.maskedAccountNumber = maskedAccountNumber;
        this.xmlns = xmlns;
        this.xsi = xsi;
        this.schemaLocation = schemaLocation;
        this.type = type;
        this.version = version;
        this.profile = profile;
        this.summary = summary;
        this.transactions = transactions;
    }

    public AccountTransactionDto() {
    }

    public String getLinkedAccountReference() {
        return linkedAccountReference;
    }

    public void setLinkedAccountReference(String linkedAccountReference) {
        this.linkedAccountReference = linkedAccountReference;
    }

    public String getMaskedAccountNumber() {
        return maskedAccountNumber;
    }

    public void setMaskedAccountNumber(String maskedAccountNumber) {
        this.maskedAccountNumber = maskedAccountNumber;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXsi() {
        return xsi;
    }

    public void setXsi(String xsi) {
        this.xsi = xsi;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ProfileDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileDto profile) {
        this.profile = profile;
    }

    public SummaryDto getSummary() {
        return summary;
    }

    public void setSummary(SummaryDto summary) {
        this.summary = summary;
    }

    public TransactionsDto getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionsDto transactions) {
        this.transactions = transactions;
    }
}