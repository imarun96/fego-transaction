package com.fego.transaction.entity;

import com.fego.transaction.common.base.BaseModel;
import com.fego.transaction.common.constants.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

@Entity
@Table(schema = Constants.SCHEMA)
public class Saving extends BaseModel {

    private long userId;
    private long goalId;
    private String ruleName;
    private LocalDate transactionDate;
    @Column(scale = 5, precision = 22)
    private BigDecimal amount;
    private long transactionId;
    private long accountId;
    private String merchant;
    private String bankName;
    private String maskedProductNumber;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
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

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}