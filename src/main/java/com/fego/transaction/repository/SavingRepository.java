package com.fego.transaction.repository;

import com.fego.transaction.common.base.BaseRepository;
import com.fego.transaction.dto.AccountOverviewDto;
import com.fego.transaction.dto.SavingsPerAccountDto;
import com.fego.transaction.entity.Saving;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Repository
public interface SavingRepository extends BaseRepository<Saving> {

    /**
     * Outputs the details of the Account from which amount has been deducted for the respective Goal with respective rules by an User.
     *
     * @param userId - Respective User Id.
     * @param goalId - Respective Goal Id.
     * @return List<AccountOverviewDto> - List of Dto which contains the account information.
     */

    @Query(value = "select NEW com.fego.transaction.dto.AccountOverviewDto (s.ruleName, s.bankName, s.maskedProductNumber, sum(s.amount)) from Saving s WHERE s.goalId = :goalId AND s.userId = :userId AND s.isDeleted = false group by 1, 2, 3")
    List<AccountOverviewDto> getAccountOverview(@Param(value = "goalId") long goalId, @Param(value = "userId") Long userId);

    /**
     * Outputs the details of Account from which amount has been deducted for all the goals of an user.
     *
     * @param userId - Respective User Id.
     * @return List<MonthlyExpenseAndIncomeDto> - List of Dto which contains the account information.
     */

    @Query(value = "select NEW com.fego.transaction.dto.SavingsPerAccountDto (s.accountId, s.goalId, sum(s.amount)) from Saving s WHERE s.goalId IN (select g.id from Goal g WHERE g.userId = :userId AND g.isDeleted = false AND g.isCompleted = false) GROUP By 1, 2")
    List<SavingsPerAccountDto> getSavingsPerAccount(@Param(value = "userId") Long userId);
}