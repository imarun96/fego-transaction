package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.RuleType;
import com.fego.transaction.common.utils.AmountRoundUpUtil;
import com.fego.transaction.common.utils.CacheDataUtil;
import com.fego.transaction.dto.*;
import com.fego.transaction.entity.Goal;
import com.fego.transaction.entity.Rule;
import com.fego.transaction.entity.Saving;
import com.fego.transaction.entity.Transaction;
import com.fego.transaction.mapper.GoalMapper;
import com.fego.transaction.mapper.SavingMapper;
import com.fego.transaction.repository.GoalRepository;
import com.fego.transaction.repository.SavingRepository;
import com.fego.transaction.repository.TransactionRepository;
import com.fego.transaction.task.SavingTask;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class SavingService extends BaseService<Saving, SavingDto, SavingTask> {

    private final IdSpecifications savingSpecifications;
    private final GoalRepository goalRepository;
    private final SavingRepository savingRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final RuleService ruleService;
    private final GoalService goalService;
    private final GoalMapper goalMapper;
    private final SavingMapper mapper;
    private final PeerUserService peerUserService;

    public SavingService(SavingRepository savingRepository, BaseMapper<Saving, SavingDto> savingMapper,
                         IdSpecifications<Saving> specifications, BaseTask<Saving> savingTask, GoalRepository goalRepository, IdSpecifications savingSpecifications, GoalMapper goalMapper, RuleService ruleService, SavingMapper mapper, TransactionRepository transactionRepository, AccountService accountService, @Lazy GoalService goalService, PeerUserService peerUserService) {
        super(savingRepository, savingMapper, specifications, savingTask);
        this.savingSpecifications = savingSpecifications;
        this.goalMapper = goalMapper;
        this.mapper = mapper;
        this.ruleService = ruleService;
        this.savingRepository = savingRepository;
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.goalRepository = goalRepository;
        this.goalService = goalService;
        this.peerUserService = peerUserService;
    }

    /**
     * Calculates the Total Savings by a particular Goal.
     *
     * @param goalDataDto - The target Dto in which the details need to be set.
     * @param goalId      - The target Goal for which Savings amount need to be calculated.
     */
    public void fetchTotalSavingAmount(GoalDataDto goalDataDto, long goalId) {
        List<AccountOverviewDto> accountOverviewDto = savingRepository.getAccountOverview(goalId, CacheDataUtil.getUserId());
        goalDataDto.setAutoDeductOverview(accountOverviewDto.stream().filter(predicate -> predicate.getRuleName().equals(RuleType.AUTO_DEDUCT.name())).collect(Collectors.toList()));
        goalDataDto.setInstantSavingOverview(accountOverviewDto.stream().filter(predicate -> predicate.getRuleName().equals(RuleType.INSTANT_SAVING_POP_UP.name())).collect(Collectors.toList()));
        goalDataDto.setRoundUpOverview(accountOverviewDto.stream().filter(predicate -> predicate.getRuleName().equals(RuleType.ROUND_UP.name())).collect(Collectors.toList()));
        Map<String, BigDecimal> totalOverview = accountOverviewDto.stream().collect(Collectors.groupingBy((AccountOverviewDto a) -> a.getBankName() + Constants.HYPHEN + a.getMaskedProductNumber(), Collectors.mapping(AccountOverviewDto::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        List<AccountOverviewDto> dto = new ArrayList<>();
        totalOverview.forEach(getStringBigDecimalBiConsumer(dto));
        goalDataDto.setTotalOverview(dto);
        Map<String, BigDecimal> savingValues = calculateSavingAmount(accountOverviewDto);
        goalDataDto.setAutoDeductAmount(savingValues.getOrDefault(RuleType.AUTO_DEDUCT.name(), BigDecimal.ZERO));
        goalDataDto.setInstantSavingAmount(savingValues.getOrDefault(RuleType.INSTANT_SAVING_POP_UP.name(), BigDecimal.ZERO));
        goalDataDto.setRoundUpAmount(savingValues.getOrDefault(RuleType.ROUND_UP.name(), BigDecimal.ZERO));
        goalDataDto.setTotalSavedAmount(goalDataDto.getAutoDeductAmount().add(goalDataDto.getInstantSavingAmount().add(goalDataDto.getRoundUpAmount())));
    }

    /**
     * Update a list of AccountOverviewDto with the required information.
     *
     * @param dto - List of AccountOverviewDto which needs to be updated.
     * @return Consumer<String, BigDecimal> - Functional Interface which returns no result.
     */
    private BiConsumer<String, BigDecimal> getStringBigDecimalBiConsumer(List<AccountOverviewDto> dto) {
        return (key, value) -> {
            AccountOverviewDto accountOverview = new AccountOverviewDto();
            accountOverview.setAmount(value);
            String[] parts = key.split(Constants.HYPHEN);
            accountOverview.setBankName(parts[0]);
            accountOverview.setMaskedProductNumber(parts[1]);
            dto.add(accountOverview);
        };
    }

    /**
     * Calculates Saving Amount for a list of AccountOverviewDto.
     *
     * @param accountOverviewDto - List of AccountOverviewDto for which amount need to be calculated.
     * @return savings - A map with Rule Name as Key and its total amount as Value.
     */
    public Map<String, BigDecimal> calculateSavingAmount(List<AccountOverviewDto> accountOverviewDto) {
        Map<String, BigDecimal> savings;
        savings = accountOverviewDto.stream().collect(Collectors.groupingBy(AccountOverviewDto::getRuleName,
                Collectors.mapping(AccountOverviewDto::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        return savings;
    }

    /**
     * Calculates Saving Amount for a list of Goals.
     *
     * @param goalIds - List of Goals for which amount need to be calculated.
     * @return BigDecimal - Total saving amount of the Goals.
     */
    public BigDecimal calculateTotalSavingAmount(List<Long> goalIds) {
        Specification<Saving> goalSpecification = savingSpecifications.findTotalSavingByGoalIds(goalIds);
        List<SavingDto> savingDto = this.findAll(goalSpecification);
        return savingDto.stream().map(SavingDto::getAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Saves Instant Save Pop Amount.
     *
     * @param instantAmountSaveRequestDto - Dto which has the amount to be saved.
     * @return consentResponseDto - A Success message if the amount is properly saved.
     */
    public SuccessResponseDto saveInstantAmount(InstantAmountSaveRequestDto instantAmountSaveRequestDto) {
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        Specification<Goal> goalSpecification = savingSpecifications.findActiveGoals(CacheDataUtil.getUserId());
        List<Goal> goals = goalRepository.findAll(goalSpecification);
        Map<Long, BigDecimal> amountPerGoal = new HashMap<>();
        goals.stream().map(goalMapper::domainToDto).forEach(getGoalDtoConsumer(amountPerGoal));
        List<SavingDto> dto = formSavingData(amountPerGoal, instantAmountSaveRequestDto.getAmount());
        savingRepository.saveAll(dto.stream().map(mapper::dtoToDomain).collect(Collectors.toList()));
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Updates the AmountPerGoal map with required values.
     *
     * @param amountPerGoal - Target map to be updated.
     * @return Consumer<String, BigDecimal> - Functional Interface which returns no result.
     */
    private Consumer<GoalDto> getGoalDtoConsumer(Map<Long, BigDecimal> amountPerGoal) {
        return goalDto -> {
            long betweenDays = Duration.between(LocalDate.now().atStartOfDay(), goalDto.getTargetDate().atStartOfDay()).toDays();
            BigDecimal amountPerDay = goalDto.getTargetAmount().divide(new BigDecimal(betweenDays), 2, RoundingMode.HALF_UP);
            BigDecimal amountPerMonth = amountPerDay.multiply(Constants.THIRTY);
            BigDecimal autoDeductAmount = ruleService.getAutoDeductAmount(goalDto.getId());
            BigDecimal amountWithoutAutoDeduct = amountPerMonth.subtract(autoDeductAmount);
            amountPerGoal.put(goalDto.getId(), amountWithoutAutoDeduct);
        };
    }

    /**
     * Updates the SavingDto with required values.
     *
     * @param amountPerGoal - Target map to be updated.
     * @param amount        - Amount to be split.
     * @return dto - List of SavingDto with required values.
     */
    private List<SavingDto> formSavingData(Map<Long, BigDecimal> amountPerGoal, BigDecimal amount) {
        long primaryAccountId = 1L;  // this value will be fetched from cache
        BigDecimal totalAmount = amountPerGoal.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        List<SavingDto> dto = new ArrayList<>();
        amountPerGoal.forEach((key, value) -> {
            SavingDto savingDto = new SavingDto();
            savingDto.setGoalId(key);
            savingDto.setRuleName(RuleType.INSTANT_SAVING_POP_UP.name());
            savingDto.setTransactionDate(LocalDate.now());
            savingDto.setAmount(calculateSplitUpAmount(totalAmount, value, amount));
            savingDto.setAccountId(primaryAccountId);
            AccountDataDto account = accountService.findById(primaryAccountId);
            savingDto.setBankName(account.getBankName());
            savingDto.setMaskedProductNumber(account.getMaskedProductNumber());
            savingDto.setUserId(CacheDataUtil.getUserId());
            savingDto.setCreatedBy(CacheDataUtil.getUserId());
            savingDto.setUpdatedBy(CacheDataUtil.getUserId());
            dto.add(savingDto);
        });
        return dto;
    }

    /**
     * Calculates Split up amount among Goals.
     *
     * @param totalAmount   - Total amount of Goals.
     * @param amountPerGoal - Amount per Goal.
     * @param toSplitAmount - Amount to split.
     * @return BigDecimal - The split up amount.
     */
    private BigDecimal calculateSplitUpAmount(BigDecimal totalAmount, BigDecimal amountPerGoal, BigDecimal toSplitAmount) {
        return amountPerGoal.divide(totalAmount, 3, RoundingMode.HALF_UP).multiply(Constants.ONE_HUNDRED).multiply(toSplitAmount)
                .divide(Constants.ONE_HUNDRED, 3, RoundingMode.HALF_UP);
    }

    /**
     * Batch job which runs everyday to save amount towards a Goal.
     *
     * @return String - Success message if the calculation has been properly carried out.
     */
    public SuccessResponseDto batchJobForSavingTable() {
        int dayOfMonth = LocalDate.now().minusDays(1).getDayOfMonth();
        int dayOfWeek = LocalDate.now().minusDays(1).getDayOfWeek().getValue();
        Specification<Goal> goalSpec = savingSpecifications.findAllActiveGoals();
        List<Goal> goalDto = goalRepository.findAll(goalSpec);
        if (!CollectionUtils.isEmpty(goalDto)) {
            int size = goalDto.size();
            goalDto.forEach(m -> {
                m.getRules().forEach(getRuleConsumer(dayOfMonth, dayOfWeek, size, m));
                List<AccountOverviewDto> expense = goalService.getExpenseAmount(m.getId());
                BigDecimal expenseAmount = !CollectionUtils.isEmpty(expense) ? expense.stream().map(AccountOverviewDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
                if ((calculateTotalSavingAmount(Collections.singletonList(m.getId())).subtract(expenseAmount)).compareTo(m.getTargetAmount()) >= 0) {
                    m.setCompleted(Boolean.TRUE);
                    m.setUpdatedBy(0L);
                    goalRepository.save(m);
                }
            });
        }
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Inserts a record into savings table when the frequency of a Goal matches current date.
     *
     * @param dayOfMonth - Day of month.
     * @param dayOfWeek  - Day of Week.
     * @param size       - Number of Goals for an user.
     * @param m          - Goal Entity which contains details about a particular Goal.
     * @return Consumer<AccountDataDto> - Functional Interface which returns no result.
     */
    private Consumer<Rule> getRuleConsumer(int dayOfMonth, int dayOfWeek, int size, Goal goal) {
        PeerUserDto peerUserDto = peerUserService.findByUserId(CacheDataUtil.getUserId());
        return n -> {
            if (n.getRuleId() == RuleType.AUTO_DEDUCT.code() && (((peerUserDto.getAutoDeductFrequency() >= 1 && peerUserDto.getAutoDeductFrequency() <= 28) && peerUserDto.getAutoDeductFrequency() == dayOfMonth) || ((peerUserDto.getAutoDeductFrequency() >= 29 && peerUserDto.getAutoDeductFrequency() <= 35) && peerUserDto.getAutoDeductFrequency() - 28 == dayOfWeek) || peerUserDto.getAutoDeductFrequency() == 36)) {
                saveAutoDeductAmount(goal, n.getAutoDeductAmount());
            } else if (n.getRuleId() == RuleType.ROUND_UP.code()) {
                if (peerUserDto.getRoundUpFrequency() == 0) {
                    Specification<Transaction> transactionSpecification = savingSpecifications.findTransactionByUser(goal.getUserId());
                    List<Transaction> transactions = transactionRepository.findAll(transactionSpecification);
                    transactions.forEach(t -> saveRoundUpAmount(goal, t, size));
                } else if (peerUserDto.getRoundUpFrequency() == 1) {
                    Specification<Transaction> transactionSpecification = savingSpecifications.findTransactionByCategories(goal.getUserId(), peerUserDto.getCategories());
                    List<Transaction> transactions = transactionRepository.findAll(transactionSpecification);
                    transactions.forEach(t -> saveRoundUpAmount(goal, t, size));
                }
            }
        };
    }

    /**
     * Save the Round-up amount in Savings table.
     *
     * @param transaction - Transaction record for which Round Up has to applied.
     * @param size        - Number of Goals.
     * @param transaction - Goal Entity which contains details about a particular Goal.
     */
    private void saveRoundUpAmount(Goal goal, Transaction transaction, int size) {
        BigDecimal roundUpAmount = AmountRoundUpUtil.roundUpAmount(transaction.getAmount(), BigDecimal.TEN);
        if (roundUpAmount.compareTo(transaction.getAmount()) > 0) {
            SavingDto dto = new SavingDto();
            dto.setGoalId(goal.getId());
            dto.setRuleName(RuleType.ROUND_UP.name());
            dto.setAmount(roundUpAmount.divide(new BigDecimal(size), 2, RoundingMode.HALF_UP));
            dto.setUserId(goal.getUserId());
            dto.setTransactionId(transaction.getId());
            dto.setMerchant(transaction.getMerchant());
            dto.setAccountId(transaction.getAccountId());
            AccountDataDto account = accountService.findById(transaction.getAccountId());
            dto.setBankName(account.getBankName());
            dto.setMaskedProductNumber(account.getMaskedProductNumber());
            this.add(dto);
        }
    }

    /**
     * Save the AutoDeduct amount in Savings table.
     *
     * @param amount - AutoDeduct amount.
     * @param m      - Goal Entity which contains details about a particular Goal.
     */
    private void saveAutoDeductAmount(Goal m, BigDecimal amount) {
        SavingDto dto = new SavingDto();
        dto.setGoalId(m.getId());
        dto.setRuleName(RuleType.AUTO_DEDUCT.name());
        dto.setAmount(amount);
        dto.setUserId(m.getUserId());
        this.add(dto);
    }

    /**
     * Returns Pagination of Savings by a particular goal.
     *
     * @param pageSize   - Number of records in a page.
     * @param pageNumber - Current page number.
     * @param sortBy     - Sort records by a column. Default (CreatedAt)
     * @param direction  - Sort records by either ASC (or) DESC.
     * @param id         - Respective Id of Goal.
     * @return Page<SavingDto> - Pagination of Savings records.
     */
    public Page<SavingDto> getSavings(int pageSize, int pageNumber, String sortBy, String direction, long id) {
        Specification<Saving> savingSpecification = savingSpecifications.findByGoalId(id);
        return this.findAll(pageNumber, pageSize, sortBy, Optional.ofNullable(savingSpecification), direction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAdd(SavingDto incomingDto) {
        incomingDto.setCreatedBy(0L);
        incomingDto.setUpdatedBy(0L);
        incomingDto.setTransactionDate(LocalDate.now());
    }
}