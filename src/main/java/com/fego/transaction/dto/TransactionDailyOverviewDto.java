package com.fego.transaction.dto;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

public class TransactionDailyOverviewDto {
    private long id;
    private String dateAndTime;
    private String merchant;
    private String merchantLogo;
    private BigDecimal amount;
    private BigDecimal savedAmount;
    private String category;
    private String location;
    private String bank;
    private String maskedProductNumber;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String merchantLogo) {
        this.merchantLogo = merchantLogo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(BigDecimal savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getMaskedProductNumber() {
        return maskedProductNumber;
    }

    public void setMaskedProductNumber(String maskedProductNumber) {
        this.maskedProductNumber = maskedProductNumber;
    }
}