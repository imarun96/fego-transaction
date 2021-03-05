package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fego.transaction.dto.integration.TierOneInvestmentTransactionsDto;
import com.fego.transaction.dto.integration.TierOneSchemeTransactionsDto;
import com.fego.transaction.dto.integration.TierTwoInvestmentTransactionsDto;
import com.fego.transaction.dto.integration.TierTwoSchemeTransactionsDto;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionsDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @JsonProperty("Transaction")
    private List<TransactionDto> transactionDto;
    @JsonProperty("Tier1SchemeTransactions")
    private TierOneSchemeTransactionsDto tierOneSchemeTransactionsDto;
    @JsonProperty("Tier2SchemeTransactions")
    private TierTwoSchemeTransactionsDto tierTwoSchemeTransactionsDto;
    @JsonProperty("Tier1InvestmentTransactions")
    private TierOneInvestmentTransactionsDto tierOneInvestmentTransactionsDto;
    @JsonProperty("Tier2InvestmentTransactions")
    private TierTwoInvestmentTransactionsDto tierTwoInvestmentTransactionsDto;

    public TierOneSchemeTransactionsDto getTier1SchemeTransactionsDto() {
        return tierOneSchemeTransactionsDto;
    }

    public void setTier1SchemeTransactionsDto(TierOneSchemeTransactionsDto tierOneSchemeTransactionsDto) {
        this.tierOneSchemeTransactionsDto = tierOneSchemeTransactionsDto;
    }

    public TierTwoSchemeTransactionsDto getTier2SchemeTransactionsDto() {
        return tierTwoSchemeTransactionsDto;
    }

    public void setTier2SchemeTransactions(TierTwoSchemeTransactionsDto tierTwoSchemeTransactionsDto) {
        this.tierTwoSchemeTransactionsDto = tierTwoSchemeTransactionsDto;
    }

    public TierOneInvestmentTransactionsDto getTier1InvestmentTransactionsDto() {
        return tierOneInvestmentTransactionsDto;
    }

    public void setTier1InvestmentTransactionsDto(TierOneInvestmentTransactionsDto tierOneInvestmentTransactionsDto) {
        this.tierOneInvestmentTransactionsDto = tierOneInvestmentTransactionsDto;
    }

    public TierTwoInvestmentTransactionsDto getTier2InvestmentTransactionsDto() {
        return tierTwoInvestmentTransactionsDto;
    }

    public void setTier2InvestmentTransactionsDto(TierTwoInvestmentTransactionsDto tierTwoInvestmentTransactionsDto) {
        this.tierTwoInvestmentTransactionsDto = tierTwoInvestmentTransactionsDto;
    }

    public List<TransactionDto> getTransactionDto() {
        return transactionDto;
    }

    public void setTransactionDto(List<TransactionDto> transactionDto) {
        this.transactionDto = transactionDto;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}