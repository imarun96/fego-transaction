package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

public class AccountBalanceDto {
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("bankName")
    private String bankName;
    @JsonProperty("maskedProductNumber")
    private String maskedProductNumber;
    @JsonProperty("availableCreditLimit")
    private BigDecimal availableCreditLimit;
    @JsonProperty("image")
    private String image;
    @JsonProperty("currentBalance")
    private BigDecimal currentBalance;

    public AccountBalanceDto(String productType, String bankName, String maskedProductNumber, BigDecimal availableCreditLimit, String image, BigDecimal currentBalance) {
        this.productType = productType;
        this.bankName = bankName;
        this.maskedProductNumber = maskedProductNumber;
        this.availableCreditLimit = availableCreditLimit;
        this.image = image;
        this.currentBalance = currentBalance;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
        this.availableCreditLimit = availableCreditLimit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getMaskedProductNumber() {
        return maskedProductNumber;
    }

    public void setMaskedProductNumber(String maskedProductNumber) {
        this.maskedProductNumber = maskedProductNumber;
    }
}