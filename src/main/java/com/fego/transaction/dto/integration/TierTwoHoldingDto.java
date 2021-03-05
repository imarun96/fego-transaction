package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TierTwoHoldingDto {

    private BigDecimal amount;
    private BigDecimal amountInTransition;
    private String blockedUnits;
    private String freeUnits;
    private String nav;
    private String schemeId;
    private String schemeName;
    private String totalUnits;
    private String totalValueOfScheme;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountInTransition() {
        return amountInTransition;
    }

    public void setAmountInTransition(BigDecimal amountInTransition) {
        this.amountInTransition = amountInTransition;
    }

    public String getBlockedUnits() {
        return blockedUnits;
    }

    public void setBlockedUnits(String blockedUnits) {
        this.blockedUnits = blockedUnits;
    }

    public String getFreeUnits() {
        return freeUnits;
    }

    public void setFreeUnits(String freeUnits) {
        this.freeUnits = freeUnits;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getTotalUnits() {
        return totalUnits;
    }

    public void setTotalUnits(String totalUnits) {
        this.totalUnits = totalUnits;
    }

    public String getTotalValueOfScheme() {
        return totalValueOfScheme;
    }

    public void setTotalValueOfScheme(String totalValueOfScheme) {
        this.totalValueOfScheme = totalValueOfScheme;
    }
}