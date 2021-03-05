package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalRuleReqDto {
    private String rule;
    private boolean isSubscribed;
    @JsonProperty("autoDeductAmount")
    private BigDecimal autoDeductAmount;
    private String frequencyPeriod;
    private Integer frequency;
    private List<String> categories;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public BigDecimal getAutoDeductAmount() {
        return autoDeductAmount;
    }

    public void setAutoDeductAmount(BigDecimal autoDeductAmount) {
        this.autoDeductAmount = autoDeductAmount;
    }

    public String getFrequencyPeriod() {
        return frequencyPeriod;
    }

    public void setFrequencyPeriod(String frequencyPeriod) {
        this.frequencyPeriod = frequencyPeriod;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }
}