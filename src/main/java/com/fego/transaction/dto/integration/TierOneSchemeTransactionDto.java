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
public class TierOneSchemeTransactionDto {
    @JsonProperty("txnId")
    private String tierOneSchemeTransactionId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("txnDate")
    private LocalDate tierOneSchemeTransactionDate;
    @JsonProperty("type")
    private String tierOneSchemeType;
    @JsonProperty("schemeId")
    private String tierOneSchemeId;
    @JsonProperty("schemeName")
    private String tierOneSchemeName;
    @JsonProperty("narration")
    private String tierOneSchemeNarration;
    @JsonProperty("allocationPercent")
    private String tierOneSchemeAllocationPercent;
    @JsonProperty("amount")
    @Column(scale = 5, precision = 22)
    private BigDecimal tierOneSchemeAmount;
    @JsonProperty("nav")
    private String tierOneSchemeNav;
    @JsonProperty("units")
    private String tierOneSchemeUnits;
    @JsonProperty("cumulativeUnits")
    private String tierOneSchemeCumulativeUnits;

    public String getTierOneSchemeTransactionId() {
        return tierOneSchemeTransactionId;
    }

    public void setTierOneSchemeTransactionId(String tierOneSchemeTransactionId) {
        this.tierOneSchemeTransactionId = tierOneSchemeTransactionId;
    }

    public LocalDate getTierOneSchemeTransactionDate() {
        return tierOneSchemeTransactionDate;
    }

    public void setTierOneSchemeTransactionDate(LocalDate tierOneSchemeTransactionDate) {
        this.tierOneSchemeTransactionDate = tierOneSchemeTransactionDate;
    }

    public String getTierOneSchemeType() {
        return tierOneSchemeType;
    }

    public void setTierOneSchemeType(String tierOneSchemeType) {
        this.tierOneSchemeType = tierOneSchemeType;
    }

    public String getTierOneSchemeId() {
        return tierOneSchemeId;
    }

    public void setTierOneSchemeId(String tierOneSchemeId) {
        this.tierOneSchemeId = tierOneSchemeId;
    }

    public String getTierOneSchemeName() {
        return tierOneSchemeName;
    }

    public void setTierOneSchemeName(String tierOneSchemeName) {
        this.tierOneSchemeName = tierOneSchemeName;
    }

    public String getTierOneSchemeNarration() {
        return tierOneSchemeNarration;
    }

    public void setTierOneSchemeNarration(String tierOneSchemeNarration) {
        this.tierOneSchemeNarration = tierOneSchemeNarration;
    }

    public String getTierOneSchemeAllocationPercent() {
        return tierOneSchemeAllocationPercent;
    }

    public void setTierOneSchemeAllocationPercent(String tierOneSchemeAllocationPercent) {
        this.tierOneSchemeAllocationPercent = tierOneSchemeAllocationPercent;
    }

    public BigDecimal getTierOneSchemeAmount() {
        return tierOneSchemeAmount;
    }

    public void setTierOneSchemeAmount(BigDecimal tierOneSchemeAmount) {
        this.tierOneSchemeAmount = tierOneSchemeAmount;
    }

    public String getTierOneSchemeNav() {
        return tierOneSchemeNav;
    }

    public void setTierOneSchemeNav(String tierOneSchemeNav) {
        this.tierOneSchemeNav = tierOneSchemeNav;
    }

    public String getTierOneSchemeUnits() {
        return tierOneSchemeUnits;
    }

    public void setTierOneSchemeUnits(String tierOneSchemeUnits) {
        this.tierOneSchemeUnits = tierOneSchemeUnits;
    }

    public String getTierOneSchemeCumulativeUnits() {
        return tierOneSchemeCumulativeUnits;
    }

    public void setTierOneSchemeCumulativeUnits(String tierOneSchemeCumulativeUnits) {
        this.tierOneSchemeCumulativeUnits = tierOneSchemeCumulativeUnits;
    }
}