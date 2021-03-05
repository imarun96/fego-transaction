package com.fego.transaction.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class DailyTransactionResponseDto {
    private BigDecimal expense;
    private BigDecimal income;
    private BigDecimal totalSaved;
    private BigDecimal autoDeduct;
    private BigDecimal instantSave;
    private List<TransactionDailyOverviewDto> transactionDailyOverviewDto;

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getTotalSaved() {
        return totalSaved;
    }

    public void setTotalSaved(BigDecimal totalSaved) {
        this.totalSaved = totalSaved;
    }

    public BigDecimal getAutoDeduct() {
        return autoDeduct;
    }

    public void setAutoDeduct(BigDecimal autoDeduct) {
        this.autoDeduct = autoDeduct;
    }

    public BigDecimal getInstantSave() {
        return instantSave;
    }

    public void setInstantSave(BigDecimal instantSave) {
        this.instantSave = instantSave;
    }

    public List<TransactionDailyOverviewDto> getTransactionDailyOverviewDto() {
        return transactionDailyOverviewDto;
    }

    public void setTransactionDailyOverviewDto(List<TransactionDailyOverviewDto> transactionDailyOverviewDto) {
        this.transactionDailyOverviewDto = transactionDailyOverviewDto;
    }
}