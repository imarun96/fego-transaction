package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalLoadResponseDto {
    private String image;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    private BigDecimal targetAmount;
    private String goalCategory;
    @JsonProperty("rules")
    private List<GoalRuleResponseDto> goalRuleResponseDto;

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

    public List<GoalRuleResponseDto> getGoalRuleResponseDto() {
        return goalRuleResponseDto;
    }

    public void setGoalRuleResponseDto(List<GoalRuleResponseDto> goalRuleResponseDto) {
        this.goalRuleResponseDto = goalRuleResponseDto;
    }
}