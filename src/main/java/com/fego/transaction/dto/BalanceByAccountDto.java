package com.fego.transaction.dto;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

public class BalanceByAccountDto {
    private long accountId;
    private String bankName;
    private String maskedProductNumber;
    private BigDecimal amount;

    public BalanceByAccountDto(long accountId, String bankName, String maskedProductNumber, BigDecimal amount) {
        this.accountId = accountId;
        this.bankName = bankName;
        this.maskedProductNumber = maskedProductNumber;
        this.amount = amount;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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