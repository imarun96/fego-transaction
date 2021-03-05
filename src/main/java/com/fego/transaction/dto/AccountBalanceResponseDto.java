package com.fego.transaction.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class AccountBalanceResponseDto {
    private BigDecimal accountBalance;
    private BigDecimal creditCardBalance;
    private List<AccountBalanceDto> accountDetails;

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getCreditCardBalance() {
        return creditCardBalance;
    }

    public void setCreditCardBalance(BigDecimal creditCardBalance) {
        this.creditCardBalance = creditCardBalance;
    }

    public List<AccountBalanceDto> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(List<AccountBalanceDto> accountDetails) {
        this.accountDetails = accountDetails;
    }
}