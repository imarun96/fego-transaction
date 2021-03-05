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
public class TierTwoSchemeTransactionDto {
    @JsonProperty("txnId")
    private String tierTwoSchemeTransactionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("txnDate")
    private LocalDate tierTwoSchemeTransactionDate;
    @JsonProperty("type")
    private String tierTwoSchemeType;
    @JsonProperty("schemeId")
    private String tierTwoSchemeId;
    @JsonProperty("schemeName")
    private String tierTwoSchemeName;
    @JsonProperty("narration")
    private String tierTwoSchemeNarration;
    @JsonProperty("allocationPercent")
    private String tierTwoSchemeAllocationPercent;
    @JsonProperty("amount")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierTwoSchemeAmount;
    @JsonProperty("nav")
    private String tierTwoSchemeNav;
    @JsonProperty("units")
    private String tierTwoSchemeUnits;
    @JsonProperty("cumulativeUnits")
    private String tierTwoSchemeCumulativeUnits;

    public String getTierTwoSchemeTransactionId() {
        return tierTwoSchemeTransactionId;
    }

    public void setTierTwoSchemeTransactionId(String tierTwoSchemeTransactionId) {
        this.tierTwoSchemeTransactionId = tierTwoSchemeTransactionId;
    }

    public LocalDate getTierTwoSchemeTransactionDate() {
        return tierTwoSchemeTransactionDate;
    }

    public void setTierTwoSchemeTransactionDate(LocalDate tierTwoSchemeTransactionDate) {
        this.tierTwoSchemeTransactionDate = tierTwoSchemeTransactionDate;
    }

    public String getTierTwoSchemeType() {
        return tierTwoSchemeType;
    }

    public void setTierTwoSchemeType(String tierTwoSchemeType) {
        this.tierTwoSchemeType = tierTwoSchemeType;
    }

    public String getTierTwoSchemeId() {
        return tierTwoSchemeId;
    }

    public void setTierTwoSchemeId(String tierTwoSchemeId) {
        this.tierTwoSchemeId = tierTwoSchemeId;
    }

    public String getTierTwoSchemeName() {
        return tierTwoSchemeName;
    }

    public void setTierTwoSchemeName(String tierTwoSchemeName) {
        this.tierTwoSchemeName = tierTwoSchemeName;
    }

    public String getTierTwoSchemeNarration() {
        return tierTwoSchemeNarration;
    }

    public void setTierTwoSchemeNarration(String tierTwoSchemeNarration) {
        this.tierTwoSchemeNarration = tierTwoSchemeNarration;
    }

    public String getTierTwoSchemeAllocationPercent() {
        return tierTwoSchemeAllocationPercent;
    }

    public void setTierTwoSchemeAllocationPercent(String tierTwoSchemeAllocationPercent) {
        this.tierTwoSchemeAllocationPercent = tierTwoSchemeAllocationPercent;
    }

    public BigDecimal getTierTwoSchemeAmount() {
        return tierTwoSchemeAmount;
    }

    public void setTierTwoSchemeAmount(BigDecimal tierTwoSchemeAmount) {
        this.tierTwoSchemeAmount = tierTwoSchemeAmount;
    }

    public String getTierTwoSchemeNav() {
        return tierTwoSchemeNav;
    }

    public void setTierTwoSchemeNav(String tierTwoSchemeNav) {
        this.tierTwoSchemeNav = tierTwoSchemeNav;
    }

    public String getTierTwoSchemeUnits() {
        return tierTwoSchemeUnits;
    }

    public void setTierTwoSchemeUnits(String tierTwoSchemeUnits) {
        this.tierTwoSchemeUnits = tierTwoSchemeUnits;
    }

    public String getTierTwoSchemeCumulativeUnits() {
        return tierTwoSchemeCumulativeUnits;
    }

    public void setTierTwoSchemeCumulativeUnits(String tierTwoSchemeCumulativeUnits) {
        this.tierTwoSchemeCumulativeUnits = tierTwoSchemeCumulativeUnits;
    }
}