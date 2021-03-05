package com.fego.transaction.dto;

import com.fego.transaction.common.base.BaseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

public class FinancialCalendarDto extends BaseDto {

    private long userId;
    private LocalDate startDateRange;
    private LocalDate endDateRange;
    private String transactionType;
    private String fegoCategory;
    private String fegoSubCategory;
    private LocalDate proposedTransactionDate;
    private LocalDate actualTransactionDate;
    private BigDecimal amount;
    private String transactionFlag;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public LocalDate getStartDateRange() {
        return startDateRange;
    }

    public void setStartDateRange(LocalDate startDateRange) {
        this.startDateRange = startDateRange;
    }

    public LocalDate getEndDateRange() {
        return endDateRange;
    }

    public void setEndDateRange(LocalDate endDateRange) {
        this.endDateRange = endDateRange;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getFegoCategory() {
        return fegoCategory;
    }

    public void setFegoCategory(String fegoCategory) {
        this.fegoCategory = fegoCategory;
    }

    public String getFegoSubCategory() {
        return fegoSubCategory;
    }

    public void setFegoSubCategory(String fegoSubCategory) {
        this.fegoSubCategory = fegoSubCategory;
    }

    public LocalDate getProposedTransactionDate() {
        return proposedTransactionDate;
    }

    public void setProposedTransactionDate(LocalDate proposedTransactionDate) {
        this.proposedTransactionDate = proposedTransactionDate;
    }

    public LocalDate getActualTransactionDate() {
        return actualTransactionDate;
    }

    public void setActualTransactionDate(LocalDate actualTransactionDate) {
        this.actualTransactionDate = actualTransactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionFlag() {
        return transactionFlag;
    }

    public void setTransactionFlag(String transactionFlag) {
        this.transactionFlag = transactionFlag;
    }
}