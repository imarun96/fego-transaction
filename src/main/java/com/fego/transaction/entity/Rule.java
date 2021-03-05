package com.fego.transaction.entity;

import com.fego.transaction.common.base.BaseModel;
import com.fego.transaction.common.constants.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Arun Balaji Rajasekaran
 */

@Entity
@Table(schema = Constants.SCHEMA)
public class Rule extends BaseModel {

    @Column(name = "goal_id")
    private long goalId;
    private Integer ruleId;
    @Column(scale = 2, precision = 22)
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