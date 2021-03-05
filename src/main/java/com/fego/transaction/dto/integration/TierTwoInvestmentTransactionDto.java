package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Embeddable
public class TierTwoInvestmentTransactionDto {
    @JsonProperty("txnId")
    private String tierTwoInvestmentTransactionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("txnDate")
    private LocalDate tierTwoInvestmentTransactionDate;
    @JsonProperty("type")
    private String tierTwoInvestmentType;
    @JsonProperty("narration")
    private String tierTwoInvestmentNarration;
    @JsonProperty("subscriberContribution")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierTwoInvestmentSubscriberContribution;
    @JsonProperty("employerContribution")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierTwoInvestmentEmployerContribution;
    @JsonProperty("totalContribution")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierTwoInvestmentTotalContribution;

    public String getTierTwoInvestmentTransactionId() {
        return tierTwoInvestmentTransactionId;
    }

    public void setTierTwoInvestmentTransactionId(String tierTwoInvestmentTransactionId) {
        this.tierTwoInvestmentTransactionId = tierTwoInvestmentTransactionId;
    }

    public LocalDate getTierTwoInvestmentTransactionDate() {
        return tierTwoInvestmentTransactionDate;
    }

    public void setTierTwoInvestmentTransactionDate(LocalDate tierTwoInvestmentTransactionDate) {
        this.tierTwoInvestmentTransactionDate = tierTwoInvestmentTransactionDate;
    }

    public String getTierTwoInvestmentType() {
        return tierTwoInvestmentType;
    }

    public void setTierTwoInvestmentType(String tierTwoInvestmentType) {
        this.tierTwoInvestmentType = tierTwoInvestmentType;
    }

    public String getTierTwoInvestmentNarration() {
        return tierTwoInvestmentNarration;
    }

    public void setTierTwoInvestmentNarration(String tierTwoInvestmentNarration) {
        this.tierTwoInvestmentNarration = tierTwoInvestmentNarration;
    }

    public BigDecimal getTierTwoInvestmentSubscriberContribution() {
        return tierTwoInvestmentSubscriberContribution;
    }

    public void setTierTwoInvestmentSubscriberContribution(BigDecimal tierTwoInvestmentSubscriberContribution) {
        this.tierTwoInvestmentSubscriberContribution = tierTwoInvestmentSubscriberContribution;
    }

    public BigDecimal getTierTwoInvestmentEmployerContribution() {
        return tierTwoInvestmentEmployerContribution;
    }

    public void setTierTwoInvestmentEmployerContribution(BigDecimal tierTwoInvestmentEmployerContribution) {
        this.tierTwoInvestmentEmployerContribution = tierTwoInvestmentEmployerContribution;
    }

    public BigDecimal getTierTwoInvestmentTotalContribution() {
        return tierTwoInvestmentTotalContribution;
    }

    public void setTierTwoInvestmentTotalContribution(BigDecimal tierTwoInvestmentTotalContribution) {
        this.tierTwoInvestmentTotalContribution = tierTwoInvestmentTotalContribution;
    }
}