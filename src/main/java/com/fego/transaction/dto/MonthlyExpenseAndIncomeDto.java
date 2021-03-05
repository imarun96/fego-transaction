package com.fego.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

public class MonthlyExpenseAndIncomeDto {
    private LocalDate proposedDate;
    private String transactionType;
    private BigDecimal amount;

    public MonthlyExpenseAndIncomeDto(LocalDate proposedDate, String transactionType, BigDecimal amount) {
        this.proposedDate = proposedDate;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public LocalDate getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(LocalDate proposedDate) {
        this.proposedDate = proposedDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}