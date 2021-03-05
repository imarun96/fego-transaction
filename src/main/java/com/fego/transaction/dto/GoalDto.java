package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fego.transaction.common.base.BaseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalDto extends BaseDto {
    @JsonIgnore
    private long userId;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    private BigDecimal targetAmount;
    private String image;
    private Integer goalCategory;
    private boolean isCompleted;
    @JsonIgnore
    private LocalDate lastPopUpDate;
    private List<RuleDto> rules;

    public LocalDate getLastPopUpDate() {
        return lastPopUpDate;
    }

    public void setLastPopUpDate(LocalDate lastPopUpDate) {
        this.lastPopUpDate = lastPopUpDate;
    }

    public Integer getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(Integer goalCategory) {
        this.goalCategory = goalCategory;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public List<RuleDto> getRules() {
        return rules;
    }

    public void setRules(List<RuleDto> rules) {
        this.rules = rules;
    }
}