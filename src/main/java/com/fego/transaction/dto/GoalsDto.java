package com.fego.transaction.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalsDto {
    private int noOfGoals;
    private BigDecimal totalTargetAmount;
    private BigDecimal totalSavedAmount;
    private List<GoalResponseDto> goalResponseDto;

    public List<GoalResponseDto> getGoalResponseDto() {
        return goalResponseDto;
    }

    public void setGoalResponseDto(List<GoalResponseDto> goalResponseDto) {
        this.goalResponseDto = goalResponseDto;
    }

    public int getNoOfGoals() {
        return noOfGoals;
    }

    public void setNoOfGoals(int noOfGoals) {
        this.noOfGoals = noOfGoals;
    }

    public BigDecimal getTotalTargetAmount() {
        return totalTargetAmount;
    }

    public void setTotalTargetAmount(BigDecimal totalTargetAmount) {
        this.totalTargetAmount = totalTargetAmount;
    }

    public BigDecimal getTotalSavedAmount() {
        return totalSavedAmount;
    }

    public void setTotalSavedAmount(BigDecimal totalSavedAmount) {
        this.totalSavedAmount = totalSavedAmount;
    }
}