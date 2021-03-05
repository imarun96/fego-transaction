package com.fego.transaction.repository;

import com.fego.transaction.common.base.BaseRepository;
import com.fego.transaction.dto.MonthlyExpenseAndIncomeDto;
import com.fego.transaction.dto.RecurringTransactionAmountDto;
import com.fego.transaction.dto.TransactionPerCategoryDto;
import com.fego.transaction.dto.TransactionSyncDto;
import com.fego.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author Arun Balaji Rajasekaran
 */

@Repository
public interface TransactionRepository extends BaseRepository<Transaction> {

    /**
     * .Outputs the amount spent by an user in a date range for each Transaction Type.
     *
     * @param userId           - Respective User Id.
     * @param monthStart       - Start range taken for calculation.
     * @param monthEnd         - End range taken for calculation.
     * @param accountTypes     - Account Types considered for calculation.
     * @param transactionTypes - Possible Account Types considered for calculation.
     * @param fegoCategory     - Possible Fego Categories considered for calculation.
     * @param fegoSubCategory  - Possible Fego Sub Categories considered for calculation.
     * @return List<MonthlyExpenseAndIncomeDto> - List of Dto which contains the Expense and Income of an user.
     */

    @Query(value = "select NEW com.fego.transaction.dto.MonthlyExpenseAndIncomeDto (t.valueDate, t.fegoTransactionType, sum(t.amount)) from Transaction t where t.userId = :userId AND t.valueDate between :monthStart AND :monthEnd AND t.accountType IN (:accountTypes) AND t.fegoCategory IN (:fegoCategory) AND t.fegoSubCategory IN (:fegoSubCategory) AND t.fegoTransactionType IN (:transactionTypes) AND t.isDeleted = false group by 1, 2")
    List<MonthlyExpenseAndIncomeDto> getExpenseAndIncome(@Param(value = "monthStart") LocalDate monthStart, @Param(value = "monthEnd") LocalDate monthEnd, @Param(value = "userId") Long userId, @Param(value = "accountTypes") List<String> accountTypes, @Param(value = "transactionTypes") List<String> transactionTypes, @Param(value = "fegoCategory") Set<String> fegoCategory, @Param(value = "fegoSubCategory") Set<String> fegoSubCategory);

    /**
     * Outputs the details of last raised consent.
     *
     * @return List<TransactionSyncDto> - List of Dto which contains the last consent request details.
     */

    @Query(value = "select NEW com.fego.transaction.dto.TransactionSyncDto (c.consentId) from Consent c WHERE c.id IN (select a.consentId from Account a WHERE a.isDeleted = false AND a.isAccountLinked = true AND a.consentId != 0) GROUP By 1")
    List<TransactionSyncDto> getConsentIdForTxnSync();

    /**
     * Outputs the details of the Transaction based on the FegoCategory and FegoSubCategory of an user only if the transactions are repeating for at least three times.
     *
     * @param users      - List of Users.
     * @param monthStart - Start range taken for calculation.
     * @param monthEnd   - End range taken for calculation.
     * @return List<TransactionPerCategoryDto> - Dto contains details of Transaction based on Transaction Type.
     */

    @Query(value = "select NEW com.fego.transaction.dto.TransactionPerCategoryDto (t.userId, t.transactionType, t.fegoTransactionType, t.fegoCategory, t.fegoSubCategory, max(t.valueDate)) from Transaction t WHERE t.isDeleted = false AND t.valueDate between :monthStart AND :monthEnd AND t.fegoTransactionType IN ('Income','Expense') AND t.isFixedTransaction = TRUE AND t.userId IN (:users) AND t.fegoCategory IS NOT NULL AND t.fegoSubCategory IS NOT NULL GROUP BY 1,2,3,4,5 HAVING COUNT(t.valueDate)>=3")
    List<TransactionPerCategoryDto> getTxnPerCategory(@Param(value = "monthStart") LocalDate monthStart, @Param(value = "monthEnd") LocalDate monthEnd, List<Long> users);

    /**
     * Outputs the amount spent for Transaction based on the FegoCategory and FegoSubCategory of an user only if the transactions are repeating for at least three times.
     *
     * @param users             - List of Users.
     * @param monthStart        - Start range taken for calculation.
     * @param monthEnd          - End range taken for calculation.
     * @param fegoCategories    - Possible Fego Categories considered for calculation.
     * @param fegoSubCategories - Possible Fego SubCategories considered for calculation.
     * @param transactionTypes  - Possible Transaction Types considered for calculation.
     * @return List<RecurringTxnAmountDto> - List of Dto which contains the account information.
     */

    @Query(value = "select NEW com.fego.transaction.dto.RecurringTransactionAmountDto (t.userId, t.fegoTransactionType, t.fegoCategory, t.fegoSubCategory, t.amount, t.valueDate) from Transaction t WHERE t.valueDate between :monthStart AND :monthEnd AND t.fegoTransactionType in ('Income','Expense') AND t.isFixedTransaction = TRUE AND t.fegoCategory IN (:fegoCategories) AND t.fegoSubCategory IN (:fegoSubCategories) AND t.userId IN (:users) AND t.transactionType IN (:transactionTypes) AND t.isDeleted = false")
    List<RecurringTransactionAmountDto> getAmountOfRecurringTransactions(@Param(value = "monthStart") LocalDate monthStart, @Param(value = "monthEnd") LocalDate monthEnd, List<Long> users, Set<String> fegoCategories, Set<String> fegoSubCategories, Set<String> transactionTypes);
}