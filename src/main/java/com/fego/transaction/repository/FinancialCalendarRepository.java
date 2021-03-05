package com.fego.transaction.repository;

import com.fego.transaction.common.base.BaseRepository;
import com.fego.transaction.dto.CategoriesFilterDto;
import com.fego.transaction.dto.MonthlyExpenseAndIncomeDto;
import com.fego.transaction.entity.FinancialCalendar;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Repository
public interface FinancialCalendarRepository extends BaseRepository<FinancialCalendar> {

    /**
     * Outputs the details of current month and next month Expense, Income of an User.
     *
     * @param userId     - Respective User Id.
     * @param monthStart - Start range taken for calculation.
     * @param monthEnd   - End range taken for calculation.
     * @return List<MonthlyExpenseAndIncomeDto> - The details of current month and next month Expense, Income of an User.
     */

    @Query(value = "select NEW com.fego.transaction.dto.MonthlyExpenseAndIncomeDto (f.proposedTransactionDate, f.transactionType, sum(f.amount)) from FinancialCalendar f where f.userId = :userId AND f.proposedTransactionDate between :monthStart AND :monthEnd AND f.isDeleted = false group by 1, 2")
    List<MonthlyExpenseAndIncomeDto> getExpenseAndIncome(@Param(value = "monthStart") LocalDate monthStart, @Param(value = "monthEnd") LocalDate monthEnd, @Param(value = "userId") Long userId);

    /**
     * Outputs the Categories of an User based on his Income and Expense.
     *
     * @param userId     - Respective User Id.
     * @param monthStart - Start range taken for calculation.
     * @param monthEnd   - End range taken for calculation.
     * @return List<CategoriesFilterDto> - Dto with categories in which an User has spend (or) may spent in current month and next month.
     */

    @Query(value = "select distinct NEW com.fego.transaction.dto.CategoriesFilterDto (f.fegoCategory, f.fegoSubCategory) from FinancialCalendar f where f.userId = :userId AND f.proposedTransactionDate between :monthStart AND :monthEnd AND f.isDeleted = false")
    List<CategoriesFilterDto> getCategoriesToFilter(@Param(value = "monthStart") LocalDate monthStart, @Param(value = "monthEnd") LocalDate monthEnd, @Param(value = "userId") Long userId);

    /**
     * Delete all the records from Financial Calendar table.
     */

    @Modifying
    @Query(value = "delete from FinancialCalendar f")
    void deleteFinancialCalendar();
}