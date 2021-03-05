package com.fego.transaction.dto;

import com.fego.transaction.common.base.BaseDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class PeerUserDto extends BaseDto {
    private long userId;
    private Integer age;
    private String gender;
    private String tier;
    private BigDecimal averageMonthlyIncome;
    private Integer noOfTransactionsPerMonth;
    private long peerGroupId;
    private BigDecimal averagePeerGoalSavingAmount;
    private Integer fegoScorePercentile;
    private BigDecimal accountAverageBalance;
    private BigDecimal averageFixedSalaryIncome;
    private BigDecimal averageFixedNonSalaryIncome;
    private BigDecimal averageVaryingIncome;
    private BigDecimal averageFixedExpenses;
    private BigDecimal averageVariableExpenses;
    private long primaryAccountId;
    private BigDecimal goalSaveAmount;
    private BigDecimal freeCashAmount;
    private Integer autoDeductFrequency;
    private Integer instantSaveFrequency;
    private Integer roundUpFrequency;
    private List<String> categories;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public BigDecimal getAverageMonthlyIncome() {
        return averageMonthlyIncome;
    }

    public void setAverageMonthlyIncome(BigDecimal averageMonthlyIncome) {
        this.averageMonthlyIncome = averageMonthlyIncome;
    }

    public Integer getNoOfTransactionsPerMonth() {
        return noOfTransactionsPerMonth;
    }

    public void setNoOfTransactionsPerMonth(Integer noOfTransactionsPerMonth) {
        this.noOfTransactionsPerMonth = noOfTransactionsPerMonth;
    }

    public long getPeerGroupId() {
        return peerGroupId;
    }

    public void setPeerGroupId(long peerGroupId) {
        this.peerGroupId = peerGroupId;
    }

    public BigDecimal getAveragePeerGoalSavingAmount() {
        return averagePeerGoalSavingAmount;
    }

    public void setAveragePeerGoalSavingAmount(BigDecimal averagePeerGoalSavingAmount) {
        this.averagePeerGoalSavingAmount = averagePeerGoalSavingAmount;
    }

    public Integer getFegoScorePercentile() {
        return fegoScorePercentile;
    }

    public void setFegoScorePercentile(Integer fegoScorePercentile) {
        this.fegoScorePercentile = fegoScorePercentile;
    }

    public BigDecimal getAccountAverageBalance() {
        return accountAverageBalance;
    }

    public void setAccountAverageBalance(BigDecimal accountAverageBalance) {
        this.accountAverageBalance = accountAverageBalance;
    }

    public BigDecimal getAverageFixedSalaryIncome() {
        return averageFixedSalaryIncome;
    }

    public void setAverageFixedSalaryIncome(BigDecimal averageFixedSalaryIncome) {
        this.averageFixedSalaryIncome = averageFixedSalaryIncome;
    }

    public BigDecimal getAverageFixedNonSalaryIncome() {
        return averageFixedNonSalaryIncome;
    }

    public void setAverageFixedNonSalaryIncome(BigDecimal averageFixedNonSalaryIncome) {
        this.averageFixedNonSalaryIncome = averageFixedNonSalaryIncome;
    }

    public BigDecimal getAverageVaryingIncome() {
        return averageVaryingIncome;
    }

    public void setAverageVaryingIncome(BigDecimal averageVaryingIncome) {
        this.averageVaryingIncome = averageVaryingIncome;
    }

    public BigDecimal getAverageFixedExpenses() {
        return averageFixedExpenses;
    }

    public void setAverageFixedExpenses(BigDecimal averageFixedExpenses) {
        this.averageFixedExpenses = averageFixedExpenses;
    }

    public BigDecimal getAverageVariableExpenses() {
        return averageVariableExpenses;
    }

    public void setAverageVariableExpenses(BigDecimal averageVariableExpenses) {
        this.averageVariableExpenses = averageVariableExpenses;
    }

    public long getPrimaryAccountId() {
        return primaryAccountId;
    }

    public void setPrimaryAccountId(long primaryAccountId) {
        this.primaryAccountId = primaryAccountId;
    }

    public BigDecimal getGoalSaveAmount() {
        return goalSaveAmount;
    }

    public void setGoalSaveAmount(BigDecimal goalSaveAmount) {
        this.goalSaveAmount = goalSaveAmount;
    }

    public BigDecimal getFreeCashAmount() {
        return freeCashAmount;
    }

    public void setFreeCashAmount(BigDecimal freeCashAmount) {
        this.freeCashAmount = freeCashAmount;
    }

    public Integer getAutoDeductFrequency() {
        return autoDeductFrequency;
    }

    public void setAutoDeductFrequency(Integer autoDeductFrequency) {
        this.autoDeductFrequency = autoDeductFrequency;
    }

    public Integer getInstantSaveFrequency() {
        return instantSaveFrequency;
    }

    public void setInstantSaveFrequency(Integer instantSaveFrequency) {
        this.instantSaveFrequency = instantSaveFrequency;
    }

    public Integer getRoundUpFrequency() {
        return roundUpFrequency;
    }

    public void setRoundUpFrequency(Integer roundUpFrequency) {
        this.roundUpFrequency = roundUpFrequency;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}