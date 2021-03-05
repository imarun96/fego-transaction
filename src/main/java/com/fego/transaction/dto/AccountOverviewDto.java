package com.fego.transaction.dto;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

public class AccountOverviewDto {
    private String ruleName;
    private String bankName;
    private String maskedProductNumber;
    private BigDecimal amount;

    public AccountOverviewDto() {
    }

    public AccountOverviewDto(String ruleName, String bankName, String maskedProductNumber, BigDecimal amount) {
        this.ruleName = ruleName;
        this.bankName = bankName;
        this.maskedProductNumber = maskedProductNumber;
        this.amount = amount;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getMaskedProductNumber() {
        return maskedProductNumber;
    }

    public void setMaskedProductNumber(String maskedProductNumber) {
        this.maskedProductNumber = maskedProductNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}