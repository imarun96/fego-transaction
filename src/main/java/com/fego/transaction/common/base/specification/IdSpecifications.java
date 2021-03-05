package com.fego.transaction.common.base.specification;

import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.RuleType;
import com.fego.transaction.common.utils.CacheDataUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class IdSpecifications<T> {

    public Specification<T> notDeleted() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDeleted"), Boolean.FALSE);
    }

    public Specification<T> findByJustStartSavingGoal() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("goalCategory"), 3), criteriaBuilder.equal(root.get(Constants.USER_ID), CacheDataUtil.getUserId()), criteriaBuilder.equal(root.get("isDeleted"), Boolean.FALSE));
    }

    public Specification<T> findAccountsForConsent() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.CONSENT_ID), 0L),
                criteriaBuilder.equal(root.get(Constants.IS_ACCOUNT_LINKED), Boolean.TRUE));
    }

    public Specification<T> findActiveGoals(long userId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.USER_ID), userId),
                criteriaBuilder.equal(root.get(Constants.IS_COMPLETED), Boolean.FALSE));
    }

    public Specification<T> findAllActiveGoals() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(Boolean.TRUE);
            return criteriaBuilder.and(criteriaBuilder.or(criteriaBuilder.equal(root.join(Constants.RULES).get(Constants.RULE_ID), RuleType.AUTO_DEDUCT.code()),
                    criteriaBuilder.equal(root.join(Constants.RULES).get(Constants.RULE_ID), RuleType.ROUND_UP.code())),
                    criteriaBuilder.equal(root.get(Constants.IS_COMPLETED), Boolean.FALSE));
        };
    }

    public Specification<T> filterGoalsByInstantSavingRule() {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.join(Constants.RULES).get(Constants.RULE_ID), RuleType.INSTANT_SAVING_POP_UP.code()), criteriaBuilder.equal(root.get(Constants.USER_ID), CacheDataUtil.getUserId()), criteriaBuilder.equal(root.get(Constants.IS_COMPLETED), Boolean.FALSE));
    }

    public Specification<T> findByAutoDeduct(long goalId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.RULE_ID), RuleType.AUTO_DEDUCT.code()),
                criteriaBuilder.equal(root.get(Constants.GOAL_ID), goalId));
    }

    public Specification<T> findByGoalId(long goalId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Constants.GOAL_ID), goalId);
    }

    public Specification<T> findById(long id) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public Specification<T> findByProductNumber(String accountReferenceNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("productNumber"), accountReferenceNumber);
    }

    public Specification<T> findByProductNumber(List<String> productNumbers) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.in(root.get("productNumber")).value(productNumbers);
    }

    public Specification<T> findByGoalIds(List<Long> goalIds) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.in(root.get("id")).value(goalIds);
    }

    public Specification<T> findTotalSavingByGoalIds(List<Long> goalIds) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.in(root.get(Constants.GOAL_ID)).value(goalIds);
    }

    public Specification<T> findByLinkAccountReferenceNumber(String linkAccountReferenceNumber) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("linkAccountReferenceNumber"), linkAccountReferenceNumber);
    }

    public Specification<T> findByUserId(Long userId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(Constants.USER_ID), userId);
    }

    public Specification<T> findTransactionByUser(long userId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.USER_ID), userId), criteriaBuilder.equal(root.get(Constants.VALUE_DATE), LocalDate.now().minusDays(1)));
    }

    public Specification<T> findTransactionByCategories(long userId, List<String> categories) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.in(root.get("fegoCategory")).value(categories), criteriaBuilder.equal(root.get(Constants.USER_ID), userId), criteriaBuilder.equal(root.get(Constants.VALUE_DATE), LocalDate.now().minusDays(1)));
    }

    public Specification<T> findCategories(String categorizationPartner) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("partnerName"), categorizationPartner);
    }

    public Specification<T> findAvgMonthlyIncome(List<Long> users) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.in(root.get(Constants.USER_ID)).value(users), criteriaBuilder.between(root.get(Constants.VALUE_DATE), LocalDate.now().minusYears(1), LocalDate.now()), criteriaBuilder.in(root.get("fegoTransactionType")).value(List.of(Constants.INCOME, Constants.EXPENSE)), criteriaBuilder.equal(root.get("isFixedTransaction"), Boolean.TRUE), root.get("fegoCategory").isNotNull(), root.get("fegoSubCategory").isNotNull());
    }

    public Specification<T> findVaryingIncome(List<Long> users) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.in(root.get(Constants.USER_ID)).value(users), criteriaBuilder.between(root.get(Constants.VALUE_DATE), LocalDate.now().minusYears(1), LocalDate.now()), criteriaBuilder.equal(root.get("isFixedTransaction"), Boolean.FALSE));
    }

    public Specification<T> findPrimaryAccount(String accountStatus, Long userId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), accountStatus), criteriaBuilder.equal(root.get(Constants.USER_ID), userId), criteriaBuilder.equal(root.get(Constants.IS_ACCOUNT_LINKED), Boolean.TRUE), criteriaBuilder.notEqual(root.get(Constants.CONSENT_ID), 0L));
    }

    public Specification<T> findMaxTransactedAccount(List<Long> accountIds, long userId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.in(root.get("accountId")).value(accountIds), criteriaBuilder.equal(root.get(Constants.USER_ID), userId), criteriaBuilder.between(root.get(Constants.VALUE_DATE), LocalDate.now().minusYears(1), LocalDate.now()));
    }

    public Specification<T> findEstimatedIncome(List<Long> users) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.in(root.get(Constants.USER_ID)).value(users), criteriaBuilder.between(root.get(Constants.VALUE_DATE), LocalDate.now().minusMonths(1), LocalDate.now()), criteriaBuilder.in(root.get("accountType")).value(List.of(Constants.SAVINGS, Constants.CURRENT)));
    }

    public Specification<T> findAllRecordsInSaving(long goalId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.between(root.get(Constants.TRANSACTION_DATE), LocalDate.now().minusMonths(1), LocalDate.now()), criteriaBuilder.equal(root.get(Constants.GOAL_ID), goalId));
    }

    public Specification<T> findTransactionByDate(LocalDate date, long userId) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.VALUE_DATE), date), criteriaBuilder.equal(root.get(Constants.USER_ID), userId));
    }

    public Specification<T> findSavingRecordsByDate(LocalDate date, long userId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.TRANSACTION_DATE), date), criteriaBuilder.equal(root.get(Constants.USER_ID), userId));
    }

    public Specification<T> findPastSavings(long userId, LocalDate start, LocalDate end) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.between(root.get(Constants.TRANSACTION_DATE), start, end), criteriaBuilder.equal(root.get(Constants.USER_ID), userId));
    }

    public Specification<T> findByConsentId(String consentId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("consentId"), consentId);
    }

    public Specification<T> findAccounts(long userId) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.and(criteriaBuilder.equal(root.get(Constants.USER_ID), userId), criteriaBuilder.in(root.get("productType")).value(List.of(Constants.SAVINGS, Constants.CURRENT)));
    }
}