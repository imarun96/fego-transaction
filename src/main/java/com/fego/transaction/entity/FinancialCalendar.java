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
public class FinancialCalendar extends BaseModel {

    @Column(nullable = false)
    private long userId;
    private LocalDate startDateRange;
    private LocalDate endDateRange;
    private String transactionType;
    private String fegoCategory;
    private String fegoSubCategory;
    private LocalDate proposedTransactionDate;
    private LocalDate actualTransactionDate;
    @Column(scale = 5, precision = 22)
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