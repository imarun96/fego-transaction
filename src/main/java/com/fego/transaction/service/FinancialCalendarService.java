package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.utils.CacheDataUtil;
import com.fego.transaction.dto.*;
import com.fego.transaction.entity.FinancialCalendar;
import com.fego.transaction.entity.Saving;
import com.fego.transaction.repository.FinancialCalendarRepository;
import com.fego.transaction.repository.TransactionRepository;
import com.fego.transaction.task.FinancialCalendarTask;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class FinancialCalendarService extends BaseService<FinancialCalendar, FinancialCalendarDto, FinancialCalendarTask> {

    private final IdSpecifications<Saving> specifications;
    private final SavingService savingService;
    private final FinancialCalendarRepository financialCalendarRepository;
    private final TransactionRepository transactionRepository;

    public FinancialCalendarService(BaseMapper<FinancialCalendar, FinancialCalendarDto> financialCalendarMapper,
                                    IdSpecifications<FinancialCalendar> financialCalendarSpecifications, FinancialCalendarRepository financialCalendarRepository,
                                    BaseTask<FinancialCalendar> financialCalendarTask, IdSpecifications<Saving> specifications, SavingService savingService, TransactionRepository transactionRepository) {
        super(financialCalendarRepository, financialCalendarMapper, financialCalendarSpecifications, financialCalendarTask);
        this.specifications = specifications;
        this.savingService = savingService;
        this.financialCalendarRepository = financialCalendarRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAdd(FinancialCalendarDto financialCalendarDto) {
        financialCalendarDto.setUserId(CacheDataUtil.getUserId());
        financialCalendarDto.setCreatedBy(CacheDataUtil.getUserId());
        financialCalendarDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * Expense, Income and Savings made by an User in a respective month.
     *
     * @param year  - The respective year.
     * @param month - The respective month.
     * @return monthlyOverviewDto - Success message if values are retrieved properly.
     */
    public MonthlyOverviewDto getMonthlyOverview(int year, int month) {
        LocalDate initial = LocalDate.of(year, month, 1);
        LocalDate monthStart = initial.withDayOfMonth(1);
        LocalDate monthEnd = initial.withDayOfMonth(initial.lengthOfMonth());
        List<MonthlyExpenseAndIncomeDto> futureData = financialCalendarRepository.getExpenseAndIncome(monthStart, monthEnd, CacheDataUtil.getUserId());
        List<CategoriesFilterDto> dto = financialCalendarRepository.getCategoriesToFilter(monthStart, monthEnd, CacheDataUtil.getUserId());
        Set<String> fegoCategory = dto.stream().map(CategoriesFilterDto::getFegoCategory).collect(Collectors.toSet());
        Set<String> fegoSubCategory = dto.stream().map(CategoriesFilterDto::getFegoSubCategory).collect(Collectors.toSet());
        List<MonthlyExpenseAndIncomeDto> pastData = transactionRepository.getExpenseAndIncome(monthStart, LocalDate.now().minusDays(1), CacheDataUtil.getUserId(), Constants.ACCOUNT_TYPES, Constants.FEGO_TRANSACTION_TYPES, fegoCategory, fegoSubCategory);
        pastData.addAll(futureData);
        Map<String, BigDecimal> totalAmount = pastData.stream().collect(Collectors.groupingBy(MonthlyExpenseAndIncomeDto::getTransactionType, Collectors.mapping(MonthlyExpenseAndIncomeDto::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        Specification<Saving> savingSpecification = specifications.findPastSavings(CacheDataUtil.getUserId(), monthStart, monthEnd);
        List<SavingDto> savings = savingService.findAll(savingSpecification);
        BigDecimal savedAmount = savings.stream().map(SavingDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        MonthlyOverviewDto monthlyOverviewDto = new MonthlyOverviewDto();
        monthlyOverviewDto.setMonthlyExpenseAndIncomeDto(pastData);
        monthlyOverviewDto.setExpense(totalAmount.getOrDefault(Constants.EXPENSE, BigDecimal.ZERO));
        monthlyOverviewDto.setIncome(totalAmount.getOrDefault(Constants.INCOME, BigDecimal.ZERO));
        monthlyOverviewDto.setSaved(savedAmount);
        return monthlyOverviewDto;
    }
}