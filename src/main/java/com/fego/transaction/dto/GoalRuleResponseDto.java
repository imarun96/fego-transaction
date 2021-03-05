package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalRuleResponseDto {
    private String rule;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}