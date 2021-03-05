package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.config.PropertyConfig;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.*;
import com.fego.transaction.common.exception.RecordNotFoundException;
import com.fego.transaction.common.utils.AmazonS3Service;
import com.fego.transaction.common.utils.AmountRoundUpUtil;
import com.fego.transaction.common.utils.CacheDataUtil;
import com.fego.transaction.common.utils.RestTemplateUtil;
import com.fego.transaction.dto.*;
import com.fego.transaction.entity.Account;
import com.fego.transaction.entity.Goal;
import com.fego.transaction.entity.Rule;
import com.fego.transaction.entity.Transaction;
import com.fego.transaction.repository.AccountRepository;
import com.fego.transaction.repository.GoalRepository;
import com.fego.transaction.repository.SavingRepository;
import com.fego.transaction.task.GoalTask;
import org.apache.commons.codec.binary.Base64;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class GoalService extends BaseService<Goal, GoalDto, GoalTask> {
    private static boolean isLoggedIn = Boolean.TRUE;
    private final AmazonS3Service amazonS3Service;
    private final IdSpecifications specifications;
    private final SavingService savingService;
    private final RuleService ruleService;
    private final AccountService accountService;
    private final PeerUserService peerUserService;
    private final TransactionService transactionService;
    private final PropertyConfig propertyConfig;
    private final RestTemplateUtil restTemplateUtil;
    private final GoalRepository goalRepository;
    private final SavingRepository savingRepository;
    private final AccountRepository accountRepository;
    private final SavingsMetaService savingsMetaService;

    public GoalService(GoalRepository goalRepository, BaseMapper<Goal, GoalDto> goalSettingMapper,
                       IdSpecifications<Goal> goalSpecifications, BaseTask<Goal> goalTask, RuleService ruleService,
                       PropertyConfig propertyConfig, SavingService savingService,
                       AmazonS3Service amazonS3Service, PeerUserService peerUserService, IdSpecifications specifications, RestTemplateUtil restTemplateUtil,
                       TransactionService transactionService, AccountService accountService,
                       SavingRepository savingRepository, AccountRepository accountRepository, SavingsMetaService savingsMetaService) {
        super(goalRepository, goalSettingMapper, goalSpecifications, goalTask);
        this.ruleService = ruleService;
        this.propertyConfig = propertyConfig;
        this.specifications = specifications;
        this.savingService = savingService;
        this.amazonS3Service = amazonS3Service;
        this.peerUserService = peerUserService;
        this.restTemplateUtil = restTemplateUtil;
        this.transactionService = transactionService;
        this.savingRepository = savingRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.goalRepository = goalRepository;
        this.savingsMetaService = savingsMetaService;
    }

    /**
     * Inserts a Goal into table.
     *
     * @param goalRequestDto - Details of the Goal received from client.
     * @return GoalLoadResponseDto - Details of inserted Goal.
     */
    public GoalLoadResponseDto add(GoalRequestDto goalRequestDto) {
        GoalDto dto = new GoalDto();
        preValidateAdd(dto, goalRequestDto);
        int goalCategory = GoalCategory.valueOf(goalRequestDto.getGoalCategory()).code();
        dto.setGoalCategory(goalCategory);
        if ((goalCategory == 0 || goalCategory == 1) && Objects.nonNull(goalRequestDto.getImage())) {
            String uniqueFileName = getUniqueFileName(goalRequestDto);
            setImageForGoal(dto, goalRequestDto, uniqueFileName);
        } else if (goalCategory == 2 || goalCategory == 3) {
            dto.setImage(null);
        } else {
            dto.setImage(Constants.DEFAULT_IMAGE);
        }
        if (goalRequestDto.getIsFirstGoal().equals(Boolean.TRUE)) {
            formRuleDtoData(goalRequestDto.getRules(), dto);
        } else {
            formRulesData(goalRequestDto.getRules(), dto);
        }
        long goalId = this.add(dto).getId();
        return getGoal(goalId);
    }

    /**
     * Updates the required fields in GoalDto from the request Dto.
     *
     * @param dto            - The details of the Goal.
     * @param goalRequestDto - Details of the Goal received from client.
     */
    private void preValidateAdd(GoalDto dto, GoalRequestDto goalRequestDto) {
        if (Objects.nonNull(goalRequestDto.getName())) {
            dto.setName(goalRequestDto.getName());
        } else {
            dto.setName(Constants.JUST_START_SAVING);
        }
        if (Objects.nonNull(goalRequestDto.getTargetDate())) {
            dto.setTargetDate(goalRequestDto.getTargetDate());
        }
        if (Objects.nonNull(goalRequestDto.getTargetAmount())) {
            dto.setTargetAmount(goalRequestDto.getTargetAmount());
        }
    }

    /**
     * Creates a unique file name for the Goal image uploaded.
     *
     * @param goalRequestDto - Details of the Goal received from client.
     * @return String - The unique file name.
     */
    private String getUniqueFileName(GoalRequestDto goalRequestDto) {
        return CacheDataUtil.getUserId() + Constants.HYPHEN + UUID.randomUUID().toString() + "." +
                goalRequestDto.getImage().substring(goalRequestDto.getImage().indexOf('/') + 1, goalRequestDto.getImage().indexOf(';'));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAdd(GoalDto goalDto) {
        goalDto.setCompleted(Boolean.FALSE);
        goalDto.setUserId(CacheDataUtil.getUserId());
        goalDto.setCreatedBy(CacheDataUtil.getUserId());
        goalDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * If current Goal is User's first goal then update the Rule Customization in Peer User table and als form Rule Dto.
     *
     * @param goalRuleDto - Goal rules received from client.
     * @param dto         - Dto in which required fields need to be updated.
     */
    private void formRuleDtoData(List<GoalRuleReqDto> goalRuleDto, GoalDto dto) {
        PeerUserDto peerUser = peerUserService.findByUserId(CacheDataUtil.getUserId());
        goalRuleDto.forEach(m -> {
            if (RuleType.AUTO_DEDUCT.name().equals(m.getRule())) {
                peerUser.setAutoDeductFrequency(AutoDeduct.valueOf(m.getFrequency(), m.getFrequencyPeriod()).id());
            } else if (RuleType.INSTANT_SAVING_POP_UP.name().equals(m.getRule())) {
                peerUser.setInstantSaveFrequency(InstantSaving.valueOf(m.getFrequencyPeriod()).code());
            } else if (RuleType.ROUND_UP.name().equals(m.getRule())) {
                if (CollectionUtils.isEmpty(m.getCategories())) {
                    peerUser.setRoundUpFrequency(0);
                } else {
                    peerUser.setRoundUpFrequency(1);
                    peerUser.setCategories(m.getCategories());
                }
            }
        });
        peerUserService.patch(peerUser);
        formRulesData(goalRuleDto, dto);
    }

    /**
     * If current Goal is not User's first goal then form Rule Dto.
     *
     * @param goalRuleDto - Goal rules received from client.
     * @param dto         - Dto in which required fields need to be updated.
     */
    private void formRulesData(List<GoalRuleReqDto> goalRuleDto, GoalDto dto) {
        List<RuleDto> goalRulesDto = new ArrayList<>();
        goalRuleDto.forEach(m -> {
            RuleDto ruleDto = new RuleDto();
            ruleDto.setCreatedBy(CacheDataUtil.getUserId());
            ruleDto.setUpdatedBy(CacheDataUtil.getUserId());
            if (m.getIsSubscribed() && RuleType.AUTO_DEDUCT.name().equals(m.getRule())) {
                ruleDto.setRuleId(RuleType.AUTO_DEDUCT.code());
                ruleDto.setAutoDeductAmount(m.getAutoDeductAmount());
                goalRulesDto.add(ruleDto);
            } else if (m.getIsSubscribed() && RuleType.INSTANT_SAVING_POP_UP.name().equals(m.getRule())) {
                ruleDto.setRuleId(RuleType.INSTANT_SAVING_POP_UP.code());
                goalRulesDto.add(ruleDto);
            } else if (m.getIsSubscribed() && RuleType.ROUND_UP.name().equals(m.getRule())) {
                ruleDto.setRuleId(RuleType.ROUND_UP.code());
                goalRulesDto.add(ruleDto);
            }
        });
        dto.setRules(goalRulesDto);
    }

    /**
     * Returns the list of Goals created by an User.
     *
     * @return goalsDto - Details of all the goals created by an User.
     */
    public GoalsDto goals() {
        GoalsDto goalsDto = new GoalsDto();
        Specification<Goal> baseSpecification = specifications.findByUserId(CacheDataUtil.getUserId());
        List<Goal> dto = goalRepository.findAll(baseSpecification);
        if (!CollectionUtils.isEmpty(dto)) {
            formGoalsDto(dto, goalsDto);
        } else {
            goalsDto.setNoOfGoals(0);
            goalsDto.setTotalTargetAmount(BigDecimal.ZERO);
            goalsDto.setTotalSavedAmount(BigDecimal.ZERO);
            goalsDto.setGoalResponseDto(Collections.emptyList());
        }
        return goalsDto;
    }

    /**
     * Updates the required fields of GoalsDto.
     *
     * @param dto      - List of Goals created by an User.
     * @param goalsDto - The target Dto.
     */
    private void formGoalsDto(List<Goal> dto, GoalsDto goalsDto) {
        List<GoalResponseDto> goals = new ArrayList<>();
        goalsDto.setNoOfGoals(dto.size());
        goalsDto.setTotalTargetAmount(calculateTotalTargetAmount());
        dto.forEach(m -> {
            GoalResponseDto goalResponseDto = new GoalResponseDto();
            goalResponseDto.setId(m.getId());
            goalResponseDto.setName(m.getName());
            goalResponseDto.setTargetAmount(m.getTargetAmount());
            goalResponseDto.setImage(m.getImage());
            goalResponseDto.setGoalCategory(GoalCategory.valueOf(m.getGoalCategory()).name());
            goalResponseDto
                    .setSavedAmount(savingService.calculateTotalSavingAmount(Collections.singletonList(m.getId())));
            goals.add(goalResponseDto);
        });
        List<Long> goalIds = dto.stream().map(Goal::getId).collect(Collectors.toList());
        goalsDto.setTotalSavedAmount(savingService.calculateTotalSavingAmount(goalIds));
        goalsDto.setGoalResponseDto(goals);
    }

    /**
     * Returns a complete overview of a Goal created by an User.
     *
     * @param goalId - The respective Id of the Goal.
     * @return goalDataDto - Overview of the goal created by an User.
     */
    public GoalDataDto goalOverview(long goalId) {
        GoalDataDto goalDataDto = new GoalDataDto();
        Optional<Goal> dto = goalRepository.findById(goalId);
        if (dto.isEmpty()) {
            throw new RecordNotFoundException(ErrorCode.RECORD_NOT_FOUND, "Record not found exception");
        }
        goalDataDto.setName(dto.get().getName());
        goalDataDto.setImage(dto.get().getImage());
        goalDataDto.setTargetAmount(dto.get().getTargetAmount());
        goalDataDto.setTargetDate(dto.get().getTargetDate());
        goalDataDto.setGoalCategory(GoalCategory.valueOf(dto.get().getGoalCategory()).name());
        List<AccountOverviewDto> expense = getExpenseAmount(goalId);
        goalDataDto.setExpenseOverview(expense);
        goalDataDto.setExpenseAmount(expense.stream().map(AccountOverviewDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
        savingService.fetchTotalSavingAmount(goalDataDto, goalId);
        for (AccountOverviewDto accountOverviewDto : expense) {
            goalDataDto.getTotalOverview().stream().filter(p -> p.getMaskedProductNumber().equals(accountOverviewDto.getMaskedProductNumber())).forEach(a -> a.setAmount(a.getAmount().subtract(accountOverviewDto.getAmount())));
        }
        BigDecimal totalSavedAmount = Objects.nonNull(goalDataDto.getTotalSavedAmount()) ? goalDataDto.getTotalSavedAmount() : BigDecimal.ZERO;
        goalDataDto.setTotalSavedAmount(totalSavedAmount.subtract(Objects.nonNull(goalDataDto.getExpenseAmount()) ? goalDataDto.getExpenseAmount() : BigDecimal.ZERO));
        return goalDataDto;
    }

    /**
     * Returns the details of a particular goal created by an user.
     *
     * @param goalId - The respective Id of the Goal.
     * @return goalLoadResponseDto - Details of the goal created by an User.
     */
    public GoalLoadResponseDto getGoal(long goalId) {
        GoalDto dto = this.findById(goalId);
        List<Integer> subscribedRules = new ArrayList<>();
        List<GoalRuleResponseDto> goalRules = new ArrayList<>();
        GoalLoadResponseDto goalLoadResponseDto = new GoalLoadResponseDto();
        goalLoadResponseDto.setName(dto.getName());
        goalLoadResponseDto.setImage(dto.getImage());
        goalLoadResponseDto.setTargetDate(dto.getTargetDate());
        goalLoadResponseDto.setTargetAmount(dto.getTargetAmount());
        goalLoadResponseDto.setGoalCategory(GoalCategory.valueOf(dto.getGoalCategory()).name());
        formRuleResponseData(dto, subscribedRules, goalRules);
        goalLoadResponseDto.setGoalRuleResponseDto(goalRules);
        List<Integer> differences = RuleType.getAll().keySet().stream().filter(element -> !subscribedRules.contains(element))
                .collect(Collectors.toList());
        differences.forEach(a -> {
            GoalRuleResponseDto unsubscribedGoalRules = new GoalRuleResponseDto();
            if (a == 0) {
                int savingPreference = getSavingPreference();
                unsubscribedGoalRules.setAutoDeductAmount(getAutoDeductAmount(savingPreference));
            }
            unsubscribedGoalRules.setRule(RuleType.valueOf(a).rule());
            unsubscribedGoalRules.setDescription(RuleType.valueOf(a).description());
            unsubscribedGoalRules.setSubscribed(Boolean.FALSE);
            goalLoadResponseDto.getGoalRuleResponseDto().add(unsubscribedGoalRules);
        });
        return goalLoadResponseDto;
    }

    /**
     * Updates the Dto with the Goal Rule details based on the request.
     *
     * @param dto             - The target dto in which fields need to be updated.
     * @param goalRules       - List of rules received from client.
     * @param subscribedRules - List of Subscribed rules.
     */
    private void formRuleResponseData(GoalDto dto, List<Integer> subscribedRules, List<GoalRuleResponseDto> goalRules) {
        dto.getRules().forEach(r -> {
            GoalRuleResponseDto goalRuleResponseDto = new GoalRuleResponseDto();
            PeerUserDto peerUserDto = peerUserService.findByUserId(CacheDataUtil.getUserId());
            int ruleId = r.getRuleId();
            subscribedRules.add(ruleId);
            if (ruleId == 0) {
                goalRuleResponseDto.setRule(RuleType.valueOf(ruleId).rule());
                goalRuleResponseDto.setDescription(RuleType.valueOf(ruleId).description());
                goalRuleResponseDto.setAutoDeductAmount(r.getAutoDeductAmount());
                AutoDeduct autoDeduct = AutoDeduct.valueOf(peerUserDto.getAutoDeductFrequency());
                goalRuleResponseDto.setFrequencyPeriod(autoDeduct.frequencyPeriod());
                goalRuleResponseDto.setFrequency(autoDeduct.frequency());
            } else if (ruleId == 1) {
                goalRuleResponseDto.setRule(RuleType.valueOf(ruleId).rule());
                goalRuleResponseDto.setDescription(RuleType.valueOf(ruleId).description());
                goalRuleResponseDto.setFrequencyPeriod(InstantSaving.valueOf(peerUserDto.getInstantSaveFrequency()).name());
            } else if (ruleId == 2) {
                goalRuleResponseDto.setRule(RuleType.valueOf(ruleId).rule());
                goalRuleResponseDto.setDescription(RuleType.valueOf(ruleId).description());
                if (peerUserDto.getRoundUpFrequency() == 0) {
                    goalRuleResponseDto.setFrequencyPeriod("EVERY_TRANSACTION");
                } else if (peerUserDto.getRoundUpFrequency() == 1) {
                    goalRuleResponseDto.setFrequencyPeriod("ROUND_UP_FOR_SPECIFIC_CATEGORIES");
                    goalRuleResponseDto.setCategories(peerUserDto.getCategories());
                }
            }
            goalRuleResponseDto.setSubscribed(Boolean.TRUE);
            goalRules.add(goalRuleResponseDto);
        });
    }

    /**
     * Returns an Estimated date when the user cannot achieve a Target amount within a particular date.
     *
     * @param estimatedDateRequestDto - The target dto with target date and target amount.
     * @return estimatedDateResponseDto - The estimated date or null value.
     */
    public EstimatedDateResponseDto getEstimatedDate(EstimatedDateRequestDto estimatedDateRequestDto) {
        EstimatedDateResponseDto estimatedDateResponseDto = new EstimatedDateResponseDto();
        BigDecimal targetAmount = estimatedDateRequestDto.getTargetAmount();
        BigDecimal freeCash = peerUserService.getFreeCashAmount();
        BigDecimal noOfMonths = new BigDecimal(Period.between(LocalDate.now(), estimatedDateRequestDto.getTargetDate()).getMonths() + 1);
        BigDecimal amountPerMonth = targetAmount.divide(noOfMonths, 4, RoundingMode.UP);
        if (amountPerMonth.compareTo(freeCash) > 0 && freeCash.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal calculatedMonths = targetAmount.divide(freeCash, 4, RoundingMode.UP);
            if (freeCash.multiply(calculatedMonths).intValue() == targetAmount.intValue()) {
                estimatedDateResponseDto.setEstimatedDate(LocalDate.now().plusMonths(calculatedMonths.intValue() - 1L).toString());
            }
        }
        return estimatedDateResponseDto;
    }

    /**
     * Returns the Goal rules.
     *
     * @param savingPreference - The Saving Preference of an user.
     * @return rulesResponseDto - The rules of a Goal along with Auto Deduct amount based on the Saving Preference.
     */
    public RulesResponseDto getRules(long savingPreference) {
        Specification<Goal> isOnlyGoalForUserSpec = specifications.findByUserId(CacheDataUtil.getUserId());
        List<Goal> goals = goalRepository.findAll(isOnlyGoalForUserSpec);
        List<GoalRuleResponseDto> goalRules = new ArrayList<>();
        RulesResponseDto rulesResponseDto = new RulesResponseDto();
        RuleType.getAll().forEach((key, value) -> {
            GoalRuleResponseDto goalRuleResponseDto = new GoalRuleResponseDto();
            goalRuleResponseDto.setRule(value.get(0));
            goalRuleResponseDto.setDescription(value.get(1));
            goalRuleResponseDto.setSubscribed(Boolean.FALSE);
            goalRuleResponseDto.setAutoDeductAmount(key == 0 ? getAutoDeductAmount(savingPreference) : null);
            rulesResponseDto.setFirstGoal(Boolean.TRUE);
            checkIsFirstGoalForUser(goals, rulesResponseDto, key, goalRuleResponseDto);
            goalRules.add(goalRuleResponseDto);
        });
        rulesResponseDto.setRules(goalRules);
        return rulesResponseDto;
    }

    /**
     * Outputs the rules for the goal which change based on whether the current goal is user's first goal (or) not.
     *
     * @param goals               - Goals of an User.
     * @param rulesResponseDto    - The rules of a Goal.
     * @param key                 - Rule Id.
     * @param goalRuleResponseDto - Dto which contains the details of Goal rules.
     */
    private void checkIsFirstGoalForUser(List<Goal> goals, RulesResponseDto rulesResponseDto, Integer key, GoalRuleResponseDto goalRuleResponseDto) {
        if (!CollectionUtils.isEmpty(goals)) {
            PeerUserDto peerUserDto = peerUserService.findByUserId(CacheDataUtil.getUserId());
            rulesResponseDto.setFirstGoal(Boolean.FALSE);
            if (key == 0) {
                AutoDeduct autoDeduct = AutoDeduct.valueOf(peerUserDto.getAutoDeductFrequency());
                goalRuleResponseDto.setFrequency(autoDeduct.frequency());
                goalRuleResponseDto.setFrequencyPeriod(autoDeduct.frequencyPeriod());
            } else if (key == 1) {
                goalRuleResponseDto.setFrequencyPeriod(InstantSaving.valueOf(peerUserDto.getInstantSaveFrequency()).name());
            } else if (key == 2) {
                if (peerUserDto.getRoundUpFrequency() == 0) {
                    goalRuleResponseDto.setFrequencyPeriod("EVERY_TRANSACTION");
                } else if (peerUserDto.getRoundUpFrequency() == 1) {
                    goalRuleResponseDto.setFrequencyPeriod("ROUND_UP_FOR_SPECIFIC_CATEGORIES");
                    goalRuleResponseDto.setCategories(peerUserDto.getCategories());
                }
            }
        }
    }

    /**
     * Calculates the Auto Deduct amount based on the Free Cash available to give an suggestion to the user.
     *
     * @param savingPreference - Saving Preference of the an user which is recorded during on-boarding.
     * @return BigDecimal - Calculated auto deduct amount.
     */
    private BigDecimal getAutoDeductAmount(long savingPreference) {
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal freeCash = peerUserService.getFreeCashAmount();
        if (savingPreference == SavingPreference.NEVER_SAVED.code()) {
            amount = freeCash.multiply(Constants.THIRTY).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP);
        } else if (savingPreference == SavingPreference.ONCE_IN_WHILE.code()) {
            amount = freeCash.multiply(Constants.FIFTY).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP);
        } else if (savingPreference == SavingPreference.AGGRESSIVE_SAVER.code()) {
            amount = freeCash.multiply(Constants.EIGHTY).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP);
        }
        return amount;
    }

    /**
     * Calculates the total target amount of all the goals created by an user.
     *
     * @return BigDecimal - Calculated Target Amount.
     */
    private BigDecimal calculateTotalTargetAmount() {
        Specification<Goal> baseSpecification = specifications.findActiveGoals(CacheDataUtil.getUserId());
        List<GoalDto> dto = this.findAll(baseSpecification);
        return !CollectionUtils.isEmpty(dto) ? dto.stream().map(GoalDto::getTargetAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validatePatch(GoalDto incomingDto) {
        incomingDto.setLastPopUpDate(LocalDate.now());
        incomingDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPatch(Goal incomingGoal, Goal toUpdateGoal) {
        if (Objects.nonNull(incomingGoal.getLastPopUpDate())) {
            toUpdateGoal.setLastPopUpDate(incomingGoal.getLastPopUpDate());
        }
    }

    /**
     * Returns options to display for Instant Saving Pop Up.
     *
     * @return List<BigDecimal> - List with no values (or) with Instant Saving Options.
     */
    public List<BigDecimal> getAmountForInstantSave() {
        List<BigDecimal> instantSavingOptions = getInstantOptions();
        Specification<Goal> numberOfGoalsSpec = specifications.filterGoalsByInstantSavingRule();
        List<GoalDto> goals = this.findAll(numberOfGoalsSpec);
        if (CollectionUtils.isEmpty(goals)) {
            return Collections.emptyList();
        }
        List<GoalDto> updatedList = goals.stream().filter(predicate -> Objects.isNull(predicate.getLastPopUpDate())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(updatedList)) {
            updatedList.forEach(this::patch);
            return instantSavingOptions;
        }
        return popUpForDateNotEmpty(goals, instantSavingOptions);
    }

    /**
     * Method to check the frequency of Instant Saving Pop up and decides whether to show pop up (or) not.
     *
     * @param goals                - Goals created by an User.
     * @param instantSavingOptions - List of calculated options with the Account balance in primary account of the current user.
     * @return List<BigDecimal> - List with no values (or) with Instant Saving Options.
     */
    private List<BigDecimal> popUpForDateNotEmpty(List<GoalDto> goals, List<BigDecimal> instantSavingOptions) {
        long count = 0;
        PeerUserDto peerUserDto = peerUserService.findByUserId(CacheDataUtil.getUserId());
        InstantSaving frequency = InstantSaving.valueOf(peerUserDto.getInstantSaveFrequency());
        switch (frequency) {
            case EVERYTIME_LOGIN:
                if (!CollectionUtils.isEmpty(instantSavingOptions) && isLoggedIn) {
                    updateLastShownDate(goals.stream().map(GoalDto::getId).collect(Collectors.toList()));
                    isLoggedIn = false;
                    return instantSavingOptions;
                }
                break;
            case ONCE_IN_A_WEEK:
                count = getFrequencyCount(goals, 7);
                break;
            case ONCE_IN_TWO_WEEK:
                count = getFrequencyCount(goals, 14);
                break;
            case ONCE_IN_MONTH:
                count = getFrequencyCount(goals, 30);
                break;
            default:
                break;
        }
        if (count >= 1) {
            updateLastShownDate(goals.stream().map(GoalDto::getId).collect(Collectors.toList()));
            return instantSavingOptions;
        }
        return Collections.emptyList();
    }

    /**
     * Once Instant saving pop up is shown, Last Pop up shown date is updated.
     *
     * @param goalIds - The list of goalIds which needs to be updated.
     */
    private void updateLastShownDate(List<Long> goalIds) {
        Specification<Goal> goalSpec = specifications.findByGoalIds(goalIds);
        List<GoalDto> goals = this.findAll(goalSpec);
        goals.forEach(this::patch);
    }

    /**
     * Returns options to display for Instant Saving Pop Up.
     *
     * @return List<BigDecimal> - Returns options when the current balance in primary account is more than 1000 or else an empty list.
     */
    private List<BigDecimal> getInstantOptions() {
        Specification<Account> baseSpecification = specifications.findPrimaryAccount(Constants.ACTIVE_ACCOUNT_STATUS,
                CacheDataUtil.getUserId());
        List<AccountDataDto> accounts = accountService.findAll(baseSpecification);
        String tenant = "HDFC"; //get from redis
        if (!CollectionUtils.isEmpty(accounts)) {
            Map<Boolean, List<AccountDataDto>> partitionedAccounts = accounts.stream().collect(Collectors.partitioningBy((AccountDataDto t) -> t.getBankName().equalsIgnoreCase(tenant)));
            List<Long> accountIds = partitionedAccounts.get(Boolean.TRUE).stream().filter(p -> p.getBankName().equalsIgnoreCase(tenant)).map(AccountDataDto::getId).collect(Collectors.toList());
            List<Long> primaryAccounts = getPrimaryAccountByMaxTxn(accountIds);
            for (Long primaryAccount : primaryAccounts) {
                AccountDataDto account = accountService.findById(primaryAccount);
                BigDecimal freeCash = peerUserService.getFreeCashAmount();
                if (account.getCurrentBalance().subtract(freeCash).compareTo(Constants.THOUSAND) > 0) {
                    return optionsForInstantSave(account, freeCash);
                }
            }
            if (!CollectionUtils.isEmpty(partitionedAccounts.get(Boolean.FALSE))) {
                Optional<AccountDataDto> nonPrimaryAccount = partitionedAccounts.get(Boolean.FALSE).stream().max(Comparator.comparing(AccountDataDto::getCurrentBalance));
                BigDecimal freeCash = peerUserService.getFreeCashAmount();
                if (nonPrimaryAccount.isPresent() && nonPrimaryAccount.get().getCurrentBalance().subtract(freeCash).compareTo(Constants.THOUSAND) > 0) {
                    return optionsForInstantSave(nonPrimaryAccount.get(), freeCash);
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * Finds the primary account from the list of Accounts based on the highest number of transaction and calculate options from that.
     *
     * @param accountIds - The list of AccountIds of an user
     * @return List<Long> - List of Primary Accounts (Since there may be a chance of getting same number of transactions for 1 (or) more accounts).
     */
    private List<Long> getPrimaryAccountByMaxTxn(List<Long> accountIds) {
        Specification<Transaction> transactionSpecification = specifications.findMaxTransactedAccount(accountIds,
                CacheDataUtil.getUserId());
        List<TransactionDto> transactions = transactionService.findAll(transactionSpecification);
        Map<Long, Map<Long, Long>> maxTxnAccounts = transactions.stream().collect(Collectors.groupingBy(TransactionDto::getAccountId,
                Collectors.groupingBy(z -> (long) LocalDate.parse(z.getValueDate()).getMonthValue(), Collectors.counting())));
        Map<Long, Long> totals = maxTxnAccounts.entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(),
                        e.getValue().entrySet().stream().sorted(Map.Entry.comparingByValue())
                                .mapToLong(Map.Entry::getValue).sum()))
                .collect(Collectors.toMap(Map.Entry::getKey, y -> y.getValue() / 12L));
        return totals.entrySet().stream().filter(entry -> entry.getValue().equals(Collections.max(totals.values())))
                .map(Map.Entry::getKey).collect(Collectors.toList());
    }

    /**
     * Returns the saving preference of an user from user service.
     *
     * @return int - Saving Preference of an user.
     */
    private int getSavingPreference() {
        return restTemplateUtil.getForEntity(Integer.class, propertyConfig.getUser().get(Constants.BASE_URL) + propertyConfig.getUser().get("saving-preference"));
    }

    /**
     * Based on the Saving Preference of an user, Free cash and current balance in primary account, the options for Instant saving is calculated.
     *
     * @param account  - Account object that is identified as primary account.
     * @param freeCash - Free Cash of an user.
     * @return List<BigDecimal> - Returns options to display for Instant Saving Pop Up.
     */
    private List<BigDecimal> optionsForInstantSave(AccountDataDto account, BigDecimal freeCash) {
        int savingPreference = getSavingPreference();
        BigDecimal amount = account.getCurrentBalance().subtract(freeCash);
        Map<String, BigDecimal> instanceSave = fetchAmountForInstantSave(amount);
        if (savingPreference == SavingPreference.NEVER_SAVED.code()) {
            return Arrays.asList(instanceSave.get("ONE"), instanceSave.get("TWO"), instanceSave.get("FIVE"));
        } else if (savingPreference == SavingPreference.ONCE_IN_WHILE.code()) {
            return Arrays.asList(instanceSave.get("ONE"), instanceSave.get("THREE"), instanceSave.get("SEVEN"));
        } else if (savingPreference == SavingPreference.AGGRESSIVE_SAVER.code()) {
            return Arrays.asList(instanceSave.get("ONE"), instanceSave.get("FIVE"), instanceSave.get("TEN"));
        }
        return Collections.emptyList();
    }

    /**
     * 1, 2, 3, 5, 7, 10 percent of amount is calculated based on the saving preference.
     *
     * @param amount - Amount to calculate Instant saving options.
     * @return List<BigDecimal> - Returns options to display for Instant Saving Pop Up.
     */
    private Map<String,
            BigDecimal> fetchAmountForInstantSave(BigDecimal amount) {
        Map<String, BigDecimal> instanceSave = new HashMap<>();
        instanceSave.put("ONE", AmountRoundUpUtil.roundUpAmount(amount.multiply(Constants.ONE).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP), BigDecimal.TEN));
        instanceSave.put("TWO", AmountRoundUpUtil.roundUpAmount(amount.multiply(Constants.TWO).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP), BigDecimal.TEN));
        instanceSave.put("THREE", AmountRoundUpUtil.roundUpAmount(amount.multiply(Constants.THREE).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP), BigDecimal.TEN));
        instanceSave.put("FIVE", AmountRoundUpUtil.roundUpAmount(amount.multiply(Constants.FIVE).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP), BigDecimal.TEN));
        instanceSave.put("SEVEN", AmountRoundUpUtil.roundUpAmount(amount.multiply(Constants.SEVEN).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP), BigDecimal.TEN));
        instanceSave.put("TEN", AmountRoundUpUtil.roundUpAmount(amount.multiply(BigDecimal.TEN).divide(Constants.ONE_HUNDRED, 2, RoundingMode.HALF_UP), BigDecimal.TEN));
        return instanceSave;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postDeleteByGid(Goal incomingGoal) {
        if (!CollectionUtils.isEmpty(incomingGoal.getRules())) {
            incomingGoal.getRules().forEach(rule ->
                    ruleService.deleteById(rule.getId()));
        }
    }

    /**
     * Updates the details of a Goal.
     *
     * @param goalId         - Id of a particular goal.
     * @param goalRequestDto - Details of the Goal received from client.
     * @return GoalLoadResponseDto - Details of inserted Goal.
     */
    public GoalLoadResponseDto updateGoal(long goalId, GoalRequestDto goalRequestDto) {
        GoalDto dto = new GoalDto();
        dto.setId(goalId);
        preValidateAdd(dto, goalRequestDto);
        int goalCategory = GoalCategory.valueOf(goalRequestDto.getGoalCategory()).code();
        dto.setGoalCategory(goalCategory);
        if ((goalCategory == 0 || goalCategory == 1) && Objects.nonNull(goalRequestDto.getImage())) {
            GoalDto goal = this.findById(goalId);
            String uniqueFileName;
            if (goal.getImage().substring(goal.getImage().lastIndexOf("/") + 1).equals(Constants.DEFAULT_IMAGE)) {
                uniqueFileName = getUniqueFileName(goalRequestDto);
            } else {
                uniqueFileName = goal.getImage().substring(goal.getImage().lastIndexOf("/") + 1);
            }
            setImageForGoal(dto, goalRequestDto, uniqueFileName);
        } else if (goalCategory == 2 || goalCategory == 3) {
            dto.setImage(null);
        } else {
            dto.setImage(Constants.DEFAULT_IMAGE);
        }
        if (!CollectionUtils.isEmpty(goalRequestDto.getRules())) {
            formRulesData(goalRequestDto.getRules(), dto);
        }
        this.update(dto);
        return getGoal(goalId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateUpdate(GoalDto incomingGoalDto) {
        incomingGoalDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preUpdate(Goal incomingGoal, Goal toUpdateGoal) {
        if (Objects.nonNull(incomingGoal.getName())) {
            toUpdateGoal.setName(incomingGoal.getName());
        }
        if (Objects.nonNull(incomingGoal.getTargetDate())) {
            toUpdateGoal.setTargetDate(incomingGoal.getTargetDate());
        }
        if (Objects.nonNull(incomingGoal.getTargetAmount())) {
            toUpdateGoal.setTargetAmount(incomingGoal.getTargetAmount());
        }
        if (Objects.nonNull(incomingGoal.getImage())) {
            toUpdateGoal.setImage(incomingGoal.getImage());
        }
        if (Objects.nonNull(incomingGoal.getGoalCategory())) {
            toUpdateGoal.setGoalCategory(incomingGoal.getGoalCategory());
        }
        if (!CollectionUtils.isEmpty(incomingGoal.getRules()) && !CollectionUtils.isEmpty(toUpdateGoal.getRules())) {
            List<Long> ruleIds = toUpdateGoal.getRules().stream().map(Rule::getId).collect(Collectors.toList());
            ruleService.deletePostTopicByIds(ruleIds);
            toUpdateGoal.setRules(new ArrayList<>());
        }
        if (Objects.nonNull(toUpdateGoal.getLastPopUpDate())) {
            incomingGoal.setLastPopUpDate(toUpdateGoal.getLastPopUpDate());
        }
        incomingGoal.setUserId(toUpdateGoal.getUserId());
    }

    /**
     * Set an unique name for the Goal image and save it in AWS S3 bucket.
     *
     * @param dto            - Target DTO in which image need to updated.
     * @param goalRequestDto - Image received from client.
     * @param uniqueFileName - Unique file name for each goal.
     */
    private void setImageForGoal(GoalDto dto, GoalRequestDto goalRequestDto, String uniqueFileName) {
        dto.setImage(uniqueFileName);
        amazonS3Service.saveFileIntoS3(propertyConfig.getAws().get(Constants.BUCKET_NAME),
                uniqueFileName, Base64.decodeBase64((goalRequestDto.getImage().substring(goalRequestDto.getImage().indexOf(Constants.COMMA) + 1)).getBytes()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preDeleteByGid(Goal toDeleteGoal) {
        toDeleteGoal.setUpdatedBy(CacheDataUtil.getUserId());
    }

    private long getFrequencyCount(List<GoalDto> goalDto, int i) {
        return goalDto.stream().map(GoalDto::getLastPopUpDate).filter(predicate -> Duration.between(predicate.atStartOfDay(), LocalDate.now().atStartOfDay()).toDays() > i).count();
    }

    /**
     * Deletes a particular Goal.
     *
     * @param goalId - Id of a particular goal.
     * @return SuccessResponseDto - A Success message if the goal has been deleted.
     */
    public SuccessResponseDto deleteGoal(long goalId) {
        this.deleteById(goalId);
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Calculates the expense amount of an user.
     *
     * @param goalId - Id of a particular goal.
     * @return List<AccountOverviewDto> - Details of the account from which expenses are made.
     */
    public List<AccountOverviewDto> getExpenseAmount(long goalId) {
        List<BalanceByAccountDto> balanceByAccountDto = accountRepository.getBalanceByAccount(Constants.ACCOUNT_TYPES, CacheDataUtil.getUserId());
        List<SavingsPerAccountDto> savingsPerAccountDto = savingRepository.getSavingsPerAccount(CacheDataUtil.getUserId());
        List<AccountOverviewDto> accountOverview = new ArrayList<>(Collections.emptyList());
        balanceByAccountDto.stream().map(BalanceByAccountDto::getAccountId).forEach(a -> {
            Optional<SavingsPerAccountDto> dto = savingsPerAccountDto.stream().filter(s -> s.getAccountId() == a).findFirst();
            if (dto.isPresent()) {
                Optional<BalanceByAccountDto> balanceByAccount = balanceByAccountDto.stream().filter(s -> s.getAccountId() == a).findFirst();
                if (balanceByAccount.isPresent() && balanceByAccount.get().getAmount().compareTo(dto.get().getAmount()) < 0) {
                    BigDecimal amountSpent = dto.get().getAmount().subtract(balanceByAccount.get().getAmount());
                    Map<Long, BigDecimal> amountPerGoal = savingsPerAccountDto.stream().filter(predicate -> predicate.getAccountId() == a).collect(Collectors.groupingBy(SavingsPerAccountDto::getGoalId, Collectors.mapping(SavingsPerAccountDto::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
                    BigDecimal totalAmount = amountPerGoal.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                    AccountOverviewDto accountOverviewDto = new AccountOverviewDto();
                    accountOverviewDto.setBankName(balanceByAccount.get().getBankName());
                    accountOverviewDto.setMaskedProductNumber(balanceByAccount.get().getMaskedProductNumber());
                    accountOverviewDto.setAmount(amountSpent.multiply(amountPerGoal.get(goalId).divide(totalAmount, 2, RoundingMode.HALF_UP)));
                    accountOverview.add(accountOverviewDto);
                }
            }
        });
        return accountOverview;
    }

    /**
     * Outputs the different categories of a Goal.
     *
     * @return goalCategoriesResponseDto - Categories of Goal.
     */
    public GoalCategoriesResponseDto getGoalCategories() {
        GoalCategoriesResponseDto goalCategoriesResponseDto = new GoalCategoriesResponseDto();
        Specification<Goal> goalSpecification = specifications.findByJustStartSavingGoal();
        Optional<Goal> justStartGoal = goalRepository.findOne(goalSpecification);
        if (justStartGoal.isEmpty()) {
            goalCategoriesResponseDto.setJustStartSavingFlag(Boolean.TRUE);
        } else {
            goalCategoriesResponseDto.setJustStartSavingFlag(Boolean.FALSE);
        }
        goalCategoriesResponseDto.setSavingsMetaDto(savingsMetaService.findAll());
        return goalCategoriesResponseDto;
    }
}