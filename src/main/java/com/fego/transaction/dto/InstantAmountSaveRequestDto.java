package com.fego.transaction.dto;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

public class InstantAmountSaveRequestDto {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}