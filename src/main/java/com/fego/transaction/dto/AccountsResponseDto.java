package com.fego.transaction.dto;

/**
 * @author Arun Balaji Rajasekaran
 */

public class AccountsResponseDto {
    private String bankImageUrl;
    private String bankName;
    private String accountType;
    private String maskedProductNumber;
    private String accountLinkReferenceNumber;
    private String type;
    private String fipId;
    private Boolean isAccountLinked;

    public String getBankImageUrl() {
        return bankImageUrl;
    }

    public void setBankImageUrl(String bankImageUrl) {
        this.bankImageUrl = bankImageUrl;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getMaskedProductNumber() {
        return maskedProductNumber;
    }

    public void setMaskedProductNumber(String maskedProductNumber) {
        this.maskedProductNumber = maskedProductNumber;
    }

    public Boolean getIsAccountLinked() {
        return isAccountLinked;
    }

    public void setIsAccountLinked(Boolean isAccountLinked) {
        this.isAccountLinked = isAccountLinked;
    }

    public String getAccountLinkReferenceNumber() {
        return accountLinkReferenceNumber;
    }

    public void setAccountLinkReferenceNumber(String accountLinkReferenceNumber) {
        this.accountLinkReferenceNumber = accountLinkReferenceNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFipId() {
        return fipId;
    }

    public void setFipId(String fipId) {
        this.fipId = fipId;
    }
}