package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalRequestDto {

    private String name;
    private String image;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    private BigDecimal targetAmount;
    private String goalCategory;
    private Boolean isFirstGoal;
    @JsonProperty("rules")
    private List<GoalRuleReqDto> rules;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(String goalCategory) {
        this.goalCategory = goalCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Boolean getIsFirstGoal() {
        return isFirstGoal;
    }

    public void setIsFirstGoal(Boolean firstGoal) {
        isFirstGoal = firstGoal;
    }

    public List<GoalRuleReqDto> getRules() {
        return rules;
    }

    public void setRules(List<GoalRuleReqDto> rules) {
        this.rules = rules;
    }
}