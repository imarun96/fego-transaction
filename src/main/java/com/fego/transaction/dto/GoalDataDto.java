package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalDataDto {
    private String name;
    private String image;
    private BigDecimal targetAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate targetDate;
    private BigDecimal autoDeductAmount;
    private List<AccountOverviewDto> autoDeductOverview;
    private BigDecimal instantSavingAmount;
    private List<AccountOverviewDto> instantSavingOverview;
    private BigDecimal roundUpAmount;
    private List<AccountOverviewDto> roundUpOverview;
    private BigDecimal expenseAmount;
    private List<AccountOverviewDto> expenseOverview;
    private BigDecimal totalSavedAmount;
    private List<AccountOverviewDto> totalOverview;
    private String goalCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public BigDecimal getAutoDeductAmount() {
        return autoDeductAmount;
    }

    public void setAutoDeductAmount(BigDecimal autoDeductAmount) {
        this.autoDeductAmount = autoDeductAmount;
    }

    public List<AccountOverviewDto> getAutoDeductOverview() {
        return autoDeductOverview;
    }

    public void setAutoDeductOverview(List<AccountOverviewDto> autoDeductOverview) {
        this.autoDeductOverview = autoDeductOverview;
    }

    public BigDecimal getInstantSavingAmount() {
        return instantSavingAmount;
    }

    public void setInstantSavingAmount(BigDecimal instantSavingAmount) {
        this.instantSavingAmount = instantSavingAmount;
    }

    public List<AccountOverviewDto> getInstantSavingOverview() {
        return instantSavingOverview;
    }

    public void setInstantSavingOverview(List<AccountOverviewDto> instantSavingOverview) {
        this.instantSavingOverview = instantSavingOverview;
    }

    public BigDecimal getRoundUpAmount() {
        return roundUpAmount;
    }

    public void setRoundUpAmount(BigDecimal roundUpAmount) {
        this.roundUpAmount = roundUpAmount;
    }

    public List<AccountOverviewDto> getRoundUpOverview() {
        return roundUpOverview;
    }

    public void setRoundUpOverview(List<AccountOverviewDto> roundUpOverview) {
        this.roundUpOverview = roundUpOverview;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public List<AccountOverviewDto> getExpenseOverview() {
        return expenseOverview;
    }

    public void setExpenseOverview(List<AccountOverviewDto> expenseOverview) {
        this.expenseOverview = expenseOverview;
    }

    public BigDecimal getTotalSavedAmount() {
        return totalSavedAmount;
    }

    public void setTotalSavedAmount(BigDecimal totalSavedAmount) {
        this.totalSavedAmount = totalSavedAmount;
    }

    public List<AccountOverviewDto> getTotalOverview() {
        return totalOverview;
    }

    public void setTotalOverview(List<AccountOverviewDto> totalOverview) {
        this.totalOverview = totalOverview;
    }

    public String getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(String goalCategory) {
        this.goalCategory = goalCategory;
    }
}