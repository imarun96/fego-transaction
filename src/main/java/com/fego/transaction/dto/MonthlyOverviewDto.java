package com.fego.transaction.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class MonthlyOverviewDto {
    private List<MonthlyExpenseAndIncomeDto> monthlyExpenseAndIncomeDto;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal saved;

    public List<MonthlyExpenseAndIncomeDto> getMonthlyExpenseAndIncomeDto() {
        return monthlyExpenseAndIncomeDto;
    }

    public void setMonthlyExpenseAndIncomeDto(List<MonthlyExpenseAndIncomeDto> monthlyExpenseAndIncomeDto) {
        this.monthlyExpenseAndIncomeDto = monthlyExpenseAndIncomeDto;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public BigDecimal getSaved() {
        return saved;
    }

    public void setSaved(BigDecimal saved) {
        this.saved = saved;
    }
}