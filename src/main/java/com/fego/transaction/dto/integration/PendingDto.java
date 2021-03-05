package com.fego.transaction.dto.integration;

/**
 * @author Arun Balaji Rajasekaran
 */

public class PendingDto {
    private String transactionType;
    private String amount;

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}