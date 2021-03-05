package com.fego.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class EarlyDateRangePaymentsDto {
    private long userId;
    private String fegoTransactionType;
    private String fegoCategory;
    private String fegoSubCategory;
    private BigDecimal amount;
    private LocalDate valueDate;
    private Integer dayNumber;
    private Integer getOverLapDayNumberPosition;
    private List<Integer> overLapDayNumber;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getValueDate() {
        return valueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        this.valueDate = valueDate;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public List<Integer> getOverLapDayNumber() {
        return overLapDayNumber;
    }

    public void setOverLapDayNumber(List<Integer> overLapDayNumber) {
        this.overLapDayNumber = overLapDayNumber;
    }

    public Integer getGetOverLapDayNumberPosition() {
        return getOverLapDayNumberPosition;
    }

    public void setGetOverLapDayNumberPosition(Integer getOverLapDayNumberPosition) {
        this.getOverLapDayNumberPosition = getOverLapDayNumberPosition;
    }
}