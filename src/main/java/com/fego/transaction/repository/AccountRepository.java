package com.fego.transaction.repository;

import com.fego.transaction.common.base.BaseRepository;
import com.fego.transaction.dto.AccountBalanceDto;
import com.fego.transaction.dto.BalanceByAccountDto;
import com.fego.transaction.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Repository
public interface AccountRepository extends BaseRepository<Account> {

    /**
     * Current balance of an User Accounts.
     *
     * @param userId       - Respective User Id.
     * @param accountTypes - Different Account Types.
     * @return List<AccountBalanceDto> - Balance of Account Types such as Current, Saving and Credit Card.
     */

    @Query(value = "select NEW com.fego.transaction.dto.AccountBalanceDto (a.productType, a.bankName, a.maskedProductNumber, a.availableCreditLimit, a.image, a.currentBalance) from Account a WHERE a.userId = :userId AND a.productType in (:accountTypes) AND a.isDeleted = false")
    List<AccountBalanceDto> getAccountBalance(@Param(value = "accountTypes") List<String> accountTypes, @Param(value = "userId") Long userId);


    /**
     * Total balance of accounts associated for an user.
     *
     * @param userId       - Respective User Id.
     * @param accountTypes - Different Account Types.
     * @return List<BalanceByAccountDto> - Total balance of an User.
     */

    @Query(value = "select NEW com.fego.transaction.dto.BalanceByAccountDto (a.id, a.bankName, a.maskedProductNumber, sum(a.currentBalance)) from Account a WHERE a.productType IN (:accountTypes) AND a.userId = :userId AND a.isAccountLinked = true AND a.consentId !=0 and a.isDeleted = false group by 1, 2, 3")
    List<BalanceByAccountDto> getBalanceByAccount(@Param(value = "accountTypes") List<String> accountTypes, @Param(value = "userId") Long userId);
}