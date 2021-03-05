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
public class TierOneInvestmentTransactionDto {
    @JsonProperty("txnId")
    private String tierOneInvestmentTransactionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("txnDate")
    private LocalDate tierOneInvestmentTransactionDate;
    @JsonProperty("type")
    private String tierOneInvestmentType;
    @JsonProperty("narration")
    private String tierOneInvestmentNarration;
    @JsonProperty("subscriberContribution")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierOneInvestmentSubscriberContribution;
    @JsonProperty("employerContribution")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierOneInvestmentEmployerContribution;
    @JsonProperty("totalContribution")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierOneInvestmentTotalContribution;

    public String getTierOneInvestmentTransactionId() {
        return tierOneInvestmentTransactionId;
    }

    public void setTierOneInvestmentTransactionId(String tierOneInvestmentTransactionId) {
        this.tierOneInvestmentTransactionId = tierOneInvestmentTransactionId;
    }

    public LocalDate getTierOneInvestmentTransactionDate() {
        return tierOneInvestmentTransactionDate;
    }

    public void setTierOneInvestmentTransactionDate(LocalDate tierOneInvestmentTransactionDate) {
        this.tierOneInvestmentTransactionDate = tierOneInvestmentTransactionDate;
    }

    public String getTierOneInvestmentType() {
        return tierOneInvestmentType;
    }

    public void setTierOneInvestmentType(String tierOneInvestmentType) {
        this.tierOneInvestmentType = tierOneInvestmentType;
    }

    public String getTierOneInvestmentNarration() {
        return tierOneInvestmentNarration;
    }

    public void setTierOneInvestmentNarration(String tierOneInvestmentNarration) {
        this.tierOneInvestmentNarration = tierOneInvestmentNarration;
    }

    public BigDecimal getTierOneInvestmentSubscriberContribution() {
        return tierOneInvestmentSubscriberContribution;
    }

    public void setTierOneInvestmentSubscriberContribution(BigDecimal tierOneInvestmentSubscriberContribution) {
        this.tierOneInvestmentSubscriberContribution = tierOneInvestmentSubscriberContribution;
    }

    public BigDecimal getTierOneInvestmentEmployerContribution() {
        return tierOneInvestmentEmployerContribution;
    }

    public void setTierOneInvestmentEmployerContribution(BigDecimal tierOneInvestmentEmployerContribution) {
        this.tierOneInvestmentEmployerContribution = tierOneInvestmentEmployerContribution;
    }

    public BigDecimal getTierOneInvestmentTotalContribution() {
        return tierOneInvestmentTotalContribution;
    }

    public void setTierOneInvestmentTotalContribution(BigDecimal tierOneInvestmentTotalContribution) {
        this.tierOneInvestmentTotalContribution = tierOneInvestmentTotalContribution;
    }
}