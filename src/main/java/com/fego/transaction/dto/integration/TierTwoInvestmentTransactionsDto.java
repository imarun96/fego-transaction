package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TierTwoInvestmentTransactionsDto {

    private TierTwoInvestmentTransactionDto tierTwoInvestmentTransactionDto;

    public TierTwoInvestmentTransactionDto getTier2InvestmentTransactionDto() {
        return tierTwoInvestmentTransactionDto;
    }

    public void setTier2InvestmentTransactionDto(TierTwoInvestmentTransactionDto tierTwoInvestmentTransactionDto) {
        this.tierTwoInvestmentTransactionDto = tierTwoInvestmentTransactionDto;
    }
}