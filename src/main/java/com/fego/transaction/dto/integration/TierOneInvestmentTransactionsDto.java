package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TierOneInvestmentTransactionsDto {

    private TierOneInvestmentTransactionDto tierOneInvestmentTransactionDto;

    public TierOneInvestmentTransactionDto getTier1InvestmentTransactionDto() {
        return tierOneInvestmentTransactionDto;
    }

    public void setTier1InvestmentTransactionDto(TierOneInvestmentTransactionDto tierOneInvestmentTransactionDto) {
        this.tierOneInvestmentTransactionDto = tierOneInvestmentTransactionDto;
    }
}