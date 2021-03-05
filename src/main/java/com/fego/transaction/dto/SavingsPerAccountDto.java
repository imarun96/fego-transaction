package com.fego.transaction.dto;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

public class SavingsPerAccountDto {
    private long accountId;
    private long goalId;
    private BigDecimal amount;

    public SavingsPerAccountDto(long accountId, long goalId, BigDecimal amount) {
        this.accountId = accountId;
        this.goalId = goalId;
        this.amount = amount;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}