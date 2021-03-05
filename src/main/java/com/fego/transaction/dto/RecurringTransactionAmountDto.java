package com.fego.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

public class RecurringTransactionAmountDto {
    private Long userId;
    private String fegoTransactionType;
    private String fegoCategory;
    private String fegoSubCategory;
    private LocalDate valueDate;
    private BigDecimal amount;
    private Integer dayNumber;

    public RecurringTransactionAmountDto(Long userId, String fegoTransactionType, String fegoCategory, String fegoSubCategory, BigDecimal amount, LocalDate valueDate) {
        this.userId = userId;
        this.fegoTransactionType = fegoTransactionType;
        this.fegoCategory = fegoCategory;
        this.fegoSubCategory = fegoSubCategory;
        this.amount = amount;
        this.valueDate = valueDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFegoTransactionType() {
        return fegoTransactionType;
    }

    public void setFegoTransactionType(String fegoTransactionType) {
        this.fegoTransactionType = fegoTransactionType;
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

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDayNumber() {
        return valueDate.getMonthValue();
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }
}