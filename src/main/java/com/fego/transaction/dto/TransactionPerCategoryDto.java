package com.fego.transaction.dto;

import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

public class TransactionPerCategoryDto {
    private long userId;
    private String type;
    private String fegoTransactionType;
    private String fegoCategory;
    private String fegoSubCategory;
    private LocalDate valueDate;

    public TransactionPerCategoryDto(long userId, String type, String fegoTransactionType, String fegoCategory, String fegoSubCategory, LocalDate valueDate) {
        this.userId = userId;
        this.type = type;
        this.fegoTransactionType = fegoTransactionType;
        this.fegoCategory = fegoCategory;
        this.fegoSubCategory = fegoSubCategory;
        this.valueDate = valueDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}