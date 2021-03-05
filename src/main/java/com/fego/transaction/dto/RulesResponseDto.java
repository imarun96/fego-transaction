package com.fego.transaction.dto;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class RulesResponseDto {
    private Boolean isFirstGoal;
    private List<GoalRuleResponseDto> rules;

    public Boolean getFirstGoal() {
        return isFirstGoal;
    }

    public void setFirstGoal(Boolean firstGoal) {
        isFirstGoal = firstGoal;
    }

    public List<GoalRuleResponseDto> getRules() {
        return rules;
    }

    public void setRules(List<GoalRuleResponseDto> rules) {
        this.rules = rules;
    }
}