package com.fego.transaction.dto;

import com.fego.transaction.common.base.BaseDto;
import net.minidev.json.annotate.JsonIgnore;

import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

public class RuleDto extends BaseDto {
    @JsonIgnore
    private long goalId;
    private Integer ruleId;
    private BigDecimal autoDeductAmount;

    public long getGoalId() {
        return goalId;
    }

    public void setGoalId(long goalId) {
        this.goalId = goalId;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public BigDecimal getAutoDeductAmount() {
        return autoDeductAmount;
    }

    public void setAutoDeductAmount(BigDecimal autoDeductAmount) {
        this.autoDeductAmount = autoDeductAmount;
    }
}