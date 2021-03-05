package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.config.PropertyConfig;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.RuleType;
import com.fego.transaction.common.utils.*;
import com.fego.transaction.dto.*;
import com.fego.transaction.dto.integration.*;
import com.fego.transaction.entity.*;
import com.fego.transaction.mapper.TransactionMapper;
import com.fego.transaction.repository.CategoryRepository;
import com.fego.transaction.repository.FinancialCalendarRepository;
import com.fego.transaction.repository.PeerUserRepository;
import com.fego.transaction.repository.TransactionRepository;
import com.fego.transaction.task.TransactionTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class TransactionService extends BaseService<Transaction, TransactionDto, TransactionTask> {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
    private final AccountService accountService;
    private final IdSpecifications specifications;
    private final ConsentDataService consentDataService;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final PropertyConfig propertyConfig;
    private final RestTemplateUtil restTemplateUtil;
    private final AmazonS3Service amazonS3Service;
    private final TransactionMapper transactionMapper;
    private final SavingService savingService;
    private final GoalService goalService;
    private final PeerUserService peerUserService;
    private final PeerUserRepository peerUserRepository;
    private final FinancialCalendarService financialCalendarService;
    private final FinancialCalendarRepository financialCalendarRepository;
    private String from = Constants.EMPTY;
    private String to = Constants.EMPTY;

    public TransactionService(BaseMapper<Transaction, TransactionDto> transactionBaseMapper,
                              TransactionRepository transactionRepository, AccountService accountService, IdSpecifications specifications,
                              BaseTask<Transaction> transactionTask, TransactionMapper transactionMapper, PropertyConfig propertyConfig,
                              AmazonS3Service amazonS3Service, RestTemplateUtil restTemplateUtil, ConsentDataService consentDataService, @Lazy GoalService goalService, PeerUserService peerUserService, SavingService savingService, PeerUserRepository peerUserRepository, CategoryRepository categoryRepository, FinancialCalendarService financialCalendarService, FinancialCalendarRepository financialCalendarRepository) {
        super(transactionRepository, transactionBaseMapper, specifications, transactionTask);
        this.accountService = accountService;
        this.specifications = specifications;
        this.propertyConfig = propertyConfig;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.amazonS3Service = amazonS3Service;
        this.restTemplateUtil = restTemplateUtil;
        this.consentDataService = consentDataService;
        this.savingService = savingService;
        this.goalService = goalService;
        this.peerUserRepository = peerUserRepository;
        this.peerUserService = peerUserService;
        this.categoryRepository = categoryRepository;
        this.financialCalendarService = financialCalendarService;
        this.financialCalendarRepository = financialCalendarRepository;
    }

    /**
     * Inserts transactions of an User and triggers Peer-User Batch.
     *
     * @param accountTransactionDto - List of Transactions received from Integration service.
     * @param event                 - Either Batch (or) On-boarding
     */
    private void save(List<AccountTransactionDto> accountTransactionDto, String event) {
        List<TransactionsDto> transactions = new ArrayList<>();
        List<TransactionDto> transaction = new ArrayList<>();
        accountTransactionDto.forEach(action -> {
            accountService.updateAccount(action.getLinkedAccountReference(), action.getType(), action.getSummary(),
                    action.getProfile().getHoldersDto().getHolderDto());
            transactions.add(action.getTransactions());
            action.getTransactions().getTransactionDto().forEach(m -> {
                updateTransactionFields(m, action);
                transaction.add(m);
            });
        });
        transactionRepository
                .saveAll(transaction.stream().map(transactionMapper::dtoToDomain).collect(Collectors.toList()));
        if (event.equals("On-board")) {
            SuccessResponseDto peerUserResponse = peerUserCalculationPerUser(CacheDataUtil.getUserId());
            logger.info("Peer User table calculation has been successfully completed for the User [{}] with Response as [{}]", CacheDataUtil.getUserId(), peerUserResponse.getStatus());
            SuccessResponseDto financialCalendarResponse = calculateFinancialCalendarPerUser(CacheDataUtil.getUserId());
            logger.info("Financial Calendar table calculation has been successfully completed for the User [{}] with Response as [{}]", CacheDataUtil.getUserId(), financialCalendarResponse.getStatus());
        }
        amazonS3Service.saveFileIntoS3(propertyConfig.getAws().get("bucketName"), "Transactions.json", JsonConvertorUtil.convertObjectIntoJson(transactions).getBytes());
    }

    /**
     * Updates basic fields of a particular Transaction Dto Object from consolidated Transaction of an Account.
     *
     * @param transactionDto - Particular Transaction on a date.
     * @param accountDto     - Consolidated Transaction of an Account.
     */
    private void updateTransactionFields(TransactionDto transactionDto, AccountTransactionDto accountDto) {
        transactionDto.setType(accountDto.getType());
        transactionDto.setStartDate(accountDto.getTransactions().getStartDate());
        transactionDto.setEndDate(accountDto.getTransactions().getEndDate());
        Specification<Account> baseSpecification = specifications.findByLinkAccountReferenceNumber(accountDto.getLinkedAccountReference());
        AccountDataDto account = accountService.findOne(baseSpecification);
        transactionDto.setAccountId(account.getId());
        transactionDto.setBankName(account.getBankName());
        transactionDto.setMaskedAccountNumber(account.getMaskedProductNumber());
        transactionDto.setAccountType(accountDto.getSummary().getAccountType()); // need to check the value
        transactionDto.setUserId(account.getUserId());
        transactionDto.setUpdatedBy(CacheDataUtil.getUserId());
        transactionDto.setCreatedBy(CacheDataUtil.getUserId());
        if (Objects.nonNull(accountDto.getTransactions().getTier1SchemeTransactionsDto())) {
            transactionDto.setTier1SchemeTransactionDto(accountDto.getTransactions().getTier1SchemeTransactionsDto().getTier1SchemeTransactionDto());
            transactionDto.setTier2SchemeTransactionDto(accountDto.getTransactions().getTier2SchemeTransactionsDto().getTier2SchemeTransactionDto());
            transactionDto.setTier1InvestmentTransactionDto(accountDto.getTransactions().getTier1InvestmentTransactionsDto().getTier1InvestmentTransactionDto());
            transactionDto.setTier2InvestmentTransactionDto(accountDto.getTransactions().getTier2InvestmentTransactionsDto().getTier2InvestmentTransactionDto());
        }
    }

    /**
     * To retrieve Transactions for a Consent Handle raised by the User.
     *
     * @param consentApproveReqDto - Object from User Service which has the consent details.
     * @return String              - A Success message if Account is linked properly.
     */
    public SuccessResponseDto addTxnData(ConsentApproveReqDto consentApproveReqDto) {
        ConsentApproveRequestDto consentApproveRequestDto = new ConsentApproveRequestDto();
        consentApproveRequestDto.setConsentHandle(consentApproveReqDto.getConsentHandle());
        consentApproveRequestDto.setOtp(consentApproveReqDto.getOtp());
        Specification<Account> baseSpecification = specifications.findAccountsForConsent();
        List<AccountDataDto> dto = accountService.findAll(baseSpecification);
        List<AccountDto> accDto = new ArrayList<>();
        dto.forEach(getAccountDataDtoConsumer(accDto));
        consentApproveRequestDto.setAccounts(accDto);
        ConsentTransactionResponseDto consentTransactionResponseDto = restTemplateUtil
                .postForEntity(ConsentTransactionResponseDto.class, propertyConfig.getIntegration().get(Constants.BASE_URL)
                        + propertyConfig.getIntegration().get("approveConsentRequest"), consentApproveRequestDto);
        SuccessResponseDto response = restTemplateUtil
                .putForEntity(SuccessResponseDto.class, propertyConfig.getUser().get(Constants.BASE_URL)
                        + propertyConfig.getUser().get("user") + "/" + dto.get(0).getUserId(), consentTransactionResponseDto.getTransactionData().get(0).getProfile().getHoldersDto());
        logger.info("Response from User Service - {}", response.getStatus());
        ConsentArtefactDataDto artefactData = consentTransactionResponseDto.getConsentArtefactData();
        long consentId = saveConsent(artefactData.getCustomerId(), artefactData);
        updateConsentInAccount(dto, consentId);
        save(consentTransactionResponseDto.getTransactionData(), "On-board");
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Update a list of AccountDto with the required information to raise Consent Request to OneMoney.
     *
     * @param accDto - List of Accounts which needs to be updated.
     * @return Consumer<AccountDataDto> - Functional Interface which returns no result.
     */
    private Consumer<AccountDataDto> getAccountDataDtoConsumer(List<AccountDto> accDto) {
        return m -> {
            AccountDto acc = new AccountDto();
            acc.setType(m.getType());
            AccountDataDto accountDataDto = new AccountDataDto();
            accountDataDto.setAccountType(m.getProductType());
            accountDataDto.setMaskedProductNumber(m.getMaskedProductNumber());
            accountDataDto.setProductNumber(m.getProductNumber());
            accountDataDto.setFipId(m.getFipId());
            accountDataDto.setUserInfo(m.getUserInfo());
            acc.setData(accountDataDto);
            accDto.add(acc);
        };
    }

    /**
     * Updates the consent id for the Accounts for which Consent has been raised.
     *
     * @param dto       - List of Accounts which needs to be updated.
     * @param consentId - Id of raised consent.
     */
    private void updateConsentInAccount(List<AccountDataDto> dto, long consentId) {
        List<String> productNumbers = new ArrayList<>();
        dto.forEach(m -> productNumbers.add(m.getProductNumber()));
        Specification<Account> spec = specifications.findByProductNumber(productNumbers);
        List<AccountDataDto> accounts = accountService.findAll(spec);
        accounts.forEach(a -> a.setConsentId(consentId));
        accounts.forEach(accountService::patch);
    }

    /**
     * Inserts the Consent details into table.
     *
     * @param customerVua  - The CustomerVua which is returned from OneMoney.
     * @param artefactData - Object which has the details of raised consent.
     * @return long - Id of the inserted consent.
     */
    private long saveConsent(String customerVua, ConsentArtefactDataDto artefactData) {
        ConsentDataDto consentDataDto = new ConsentDataDto();
        validatePreAdd(consentDataDto, customerVua, artefactData, from, to);
        from = Constants.EMPTY;
        to = Constants.EMPTY;
        logger.info("Consent Details has been saved in the Database.");
        return consentDataService.add(consentDataDto).getId();
    }

    /**
     * Retrieves all the User Details from User Service.
     *
     * @return List<UserDetailsDto> - List of users details.
     */
    private List<UserDetailsDto> getAllUsers() {
        return restTemplateUtil.getForList(UserDetailsDto.class, propertyConfig.getUser().get(Constants.BASE_URL) + propertyConfig.getUser().get("user"));
    }

    /**
     * Calculates Free Cash for particular user.
     *
     * @param userId - The id of particular user for which Free Cash has to be calculated.
     * @return String - Success message if free cash is calculated properly.
     */
    public SuccessResponseDto peerUserCalculationPerUser(long userId) {
        return peerUserCalculation(getAllUsers().stream().filter(p -> p.getId() == userId).collect(Collectors.toList()));
    }

    /**
     * Calculates Free Cash for all users.
     *
     * @return String - Success message if free cash is calculated properly.
     */
    public SuccessResponseDto peerUserCalculationForAllUser() {
        return peerUserCalculation(getAllUsers());
    }

    /**
     * Calculates Average Fixed Salary Income, Average Fixed Non Salary Income, Average Fixed Expense for list of users.
     *
     * @param users - List of Users for which fixed values need to be calculated.
     * @return fixedValuesOfUser - Map of all the calculated values.
     */
    private Map<String, Map<Long, BigDecimal>> getFixedValuesOfUser(List<Long> users) {
        Specification<Transaction> fixedSalarySpecification = specifications.findAvgMonthlyIncome(users);
        List<TransactionDto> fixedTransactions = this.findAll(fixedSalarySpecification);
        Map<Long, BigDecimal> avgFixedSalaryIncome = new HashMap<>();
        Map<Long, BigDecimal> avgFixedNonSalaryIncome = new HashMap<>();
        Map<Long, BigDecimal> avgFixedExpense = new HashMap<>();
        if (!CollectionUtils.isEmpty(fixedTransactions)) {
            avgFixedSalaryIncome = fixedTransactions.stream().filter(predicate -> (predicate.getFegoTransactionType().equals(Constants.INCOME)) && (predicate.getFegoCategory().equals(Constants.SALARY) || predicate.getFegoCategory().equals(Constants.RETIREMENT_INCOME))).collect(Collectors.groupingBy(TransactionDto::getUserId,
                    Collectors.mapping(TransactionDto::getAmount, Constants.avgCollector)));
            avgFixedNonSalaryIncome = fixedTransactions.stream().filter(predicate -> (predicate.getFegoTransactionType().equals(Constants.INCOME)) && (!predicate.getFegoCategory().equals(Constants.SALARY) || !predicate.getFegoCategory().equals(Constants.RETIREMENT_INCOME))).collect(Collectors.groupingBy(TransactionDto::getUserId,
                    Collectors.mapping(TransactionDto::getAmount, Constants.avgCollector)));
            avgFixedExpense = fixedTransactions.stream().filter(predicate -> predicate.getFegoTransactionType().equals(Constants.EXPENSE)).collect(Collectors.groupingBy(TransactionDto::getUserId,
                    Collectors.mapping(TransactionDto::getAmount, Constants.avgCollector)));
        }
        Map<String, Map<Long, BigDecimal>> fixedValuesOfUser = new HashMap<>();
        fixedValuesOfUser.put("avgFixedSalaryIncome", avgFixedSalaryIncome);
        fixedValuesOfUser.put("avgFixedNonSalaryIncome", avgFixedNonSalaryIncome);
        fixedValuesOfUser.put("avgFixedExpense", avgFixedExpense);
        return fixedValuesOfUser;
    }

    /**
     * Calculates Average Varying Income, Average Varying Expense Income for list of users.
     *
     * @param users - List of Users for which varying values need to be calculated.
     * @return varyingValuesOfUser - Map of all the calculated values.
     */
    private Map<String, Map<Long, BigDecimal>> getVaryingValuesOfUser(List<Long> users) {
        Specification<Transaction> varyingSalarySpecification = specifications.findVaryingIncome(users);
        List<TransactionDto> varyingTransactions = this.findAll(varyingSalarySpecification);
        Map<Boolean, List<TransactionDto>> varyingTxn;
        Map<Long, BigDecimal> varyingIncome = new HashMap<>();
        Map<Long, BigDecimal> varyingExpense = new HashMap<>();
        if (!CollectionUtils.isEmpty(varyingTransactions)) {
            varyingTxn
                    = varyingTransactions
                    .stream()
                    .collect(Collectors.partitioningBy((TransactionDto t) -> t.getFegoTransactionType().equals(Constants.INCOME)));
            if (!CollectionUtils.isEmpty(varyingTxn.get(Boolean.TRUE))) {
                varyingIncome = varyingTxn.get(Boolean.TRUE).stream().filter(predicate -> predicate.getFegoTransactionType().equals(Constants.INCOME) || predicate.getFegoCategory().equals(Constants.SALARY) || predicate.getFegoCategory().equals(Constants.RETIREMENT_INCOME)).collect(Collectors.groupingBy(TransactionDto::getUserId,
                        Collectors.mapping(TransactionDto::getAmount, Constants.avgCollector)));
            }
            if (!CollectionUtils.isEmpty(varyingTxn.get(Boolean.FALSE))) {
                varyingExpense = varyingTxn.get(Boolean.FALSE).stream().filter(predicate -> predicate.getFegoTransactionType().equals(Constants.INCOME) || predicate.getFegoCategory().equals(Constants.SALARY) || predicate.getFegoCategory().equals(Constants.RETIREMENT_INCOME)).collect(Collectors.groupingBy(TransactionDto::getUserId,
                        Collectors.mapping(TransactionDto::getAmount, Constants.avgCollector)));
            }
        }
        Map<String, Map<Long, BigDecimal>> varyingValuesOfUser = new HashMap<>();
        varyingValuesOfUser.put("varyingIncome", varyingIncome);
        varyingValuesOfUser.put("varyingExpense", varyingExpense);
        return varyingValuesOfUser;
    }

    /**
     * Calculates Average Estimated Income of users.
     *
     * @param users - List of Users for which estimated income need to be calculated.
     * @return estimatedIncomeOfUser - Map of calculated values.
     */
    private Map<String, Map<Long, BigDecimal>> getEstimatedIncomeOfUser(List<Long> users) {
        Specification<Transaction> estimatedIncomeSpecification = specifications.findEstimatedIncome(users);
        List<TransactionDto> estimatedIncomeTransaction = this.findAll(estimatedIncomeSpecification);
        Map<Long, BigDecimal> estimatedIncome = new HashMap<>();
        Map<Long, Map<Long, BigDecimal>> estimated;
        if (!CollectionUtils.isEmpty(estimatedIncomeTransaction)) {
            estimated = estimatedIncomeTransaction.stream().collect(Collectors.groupingBy(TransactionDto::getUserId,
                    Collectors.groupingBy(TransactionDto::getAccountId, Collectors.mapping(TransactionDto::getCurrentBalance, Constants.avgCollector))));
            estimatedIncome = estimated.entrySet().stream()
                    .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(),
                            e.getValue().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add)))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        Map<String, Map<Long, BigDecimal>> estimatedIncomeOfUser = new HashMap<>();
        estimatedIncomeOfUser.put("estimatedIncome", estimatedIncome);
        return estimatedIncomeOfUser;
    }

    /**
     * Calculates the amount spent for Goals by an User.
     *
     * @param userId - User Id for which Goal amount need to be calculated.
     * @return BigDecimal - Amount spent by the user for Goals.
     */
    private BigDecimal getGoalAmountOfUser(long userId) {
        Specification<Goal> goalSpecification = specifications.findActiveGoals(userId);
        List<GoalDto> activeGoalsOfUser = goalService.findAll(goalSpecification);
        if (!CollectionUtils.isEmpty(activeGoalsOfUser)) {
            List<BigDecimal> savingsOfAllGoals = new ArrayList<>();
            activeGoalsOfUser.forEach(goal -> {
                BigDecimal savingsPerGoal;
                Specification<Saving> savingSpecification = specifications.findAllRecordsInSaving(goal.getId());
                List<SavingDto> savingsOfGoal = savingService.findAll(savingSpecification);
                if (!CollectionUtils.isEmpty(savingsOfGoal)) {
                    savingsPerGoal = savingsOfGoal.stream().filter(predicate -> predicate.getGoalId() == goal.getId()).map(SavingDto::getAmount).collect(Constants.avgCollector);
                } else {
                    Optional<BigDecimal> amount = goal.getRules().stream().filter(r -> r.getRuleId() == RuleType.AUTO_DEDUCT.code()).map(RuleDto::getAutoDeductAmount).findFirst();
                    BigDecimal noOfMonths = new BigDecimal(Period.between(LocalDate.now(), goal.getTargetDate()).getMonths() + 1);
                    BigDecimal amountPerMonth = goal.getTargetAmount().divide(noOfMonths, 4, RoundingMode.UP);
                    if (amount.isPresent() && amount.get().compareTo(BigDecimal.ZERO) != 0) {
                        savingsPerGoal = amountPerMonth.min(amount.get());
                    } else {
                        savingsPerGoal = amountPerMonth;
                    }
                }
                savingsOfAllGoals.add(savingsPerGoal);
            });
            return savingsOfAllGoals.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculates the Free Cash amount for the List of Users based on the Fixed, Varying and estimated income.
     *
     * @param users - List of users for which free cash need to calculated.
     * @return String - Success message if data is properly calculated.
     */
    public SuccessResponseDto peerUserCalculation(List<UserDetailsDto> users) {
        List<Long> userIds = users.stream().map(UserDetailsDto::getId).collect(Collectors.toList());
        Map<String, Map<Long, BigDecimal>> fixedValuesOfUser = getFixedValuesOfUser(userIds);
        Map<String, Map<Long, BigDecimal>> varyingValuesOfUser = getVaryingValuesOfUser(userIds);
        Map<String, Map<Long, BigDecimal>> estimatedIncomeOfUser = getEstimatedIncomeOfUser(userIds);
        for (UserDetailsDto user : users) {
            Specification<PeerUser> peerUserSpecification = specifications.findByUserId(user.getId());
            Optional<PeerUser> entity = peerUserRepository.findOne(peerUserSpecification);
            PeerUserDto peerUserDto = new PeerUserDto();
            validatePeerUser(user, peerUserDto, fixedValuesOfUser, varyingValuesOfUser, estimatedIncomeOfUser);
            if (entity.isPresent()) {
                peerUserDto.setId(entity.get().getId());
                peerUserService.patch(peerUserDto);
            } else {
                peerUserService.add(peerUserDto);
            }
        }
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Update the fields of Peer User Dto before inserting.
     *
     * @param userDto               - Details of an User.
     * @param peerUserDto           - Dto in which details need to be updated.
     * @param fixedValuesOfUser     - Fixed values of an User.
     * @param varyingValuesOfUser   - Varying values of an User.
     * @param estimatedIncomeOfUser - Estimated income of an User.
     */
    private void validatePeerUser(UserDetailsDto userDto, PeerUserDto peerUserDto, Map<String, Map<Long, BigDecimal>> fixedValuesOfUser, Map<String, Map<Long, BigDecimal>> varyingValuesOfUser, Map<String, Map<Long, BigDecimal>> estimatedIncomeOfUser) {
        peerUserDto.setUserId(userDto.getId());
        BigDecimal avgFixedSalaryIncomeByUser = fixedValuesOfUser.get("avgFixedSalaryIncome").getOrDefault(userDto.getId(), BigDecimal.ZERO);
        BigDecimal avgFixedNonSalaryIncomeByUser = fixedValuesOfUser.get("avgFixedNonSalaryIncome").getOrDefault(userDto.getId(), BigDecimal.ZERO);
        BigDecimal avgFixedExpenseByUser = fixedValuesOfUser.get("avgFixedExpense").getOrDefault(userDto.getId(), BigDecimal.ZERO);
        BigDecimal varyingIncomeByUser = varyingValuesOfUser.get("varyingIncome").getOrDefault(userDto.getId(), BigDecimal.ZERO);
        BigDecimal varyingExpenseByUser = varyingValuesOfUser.get("varyingExpense").getOrDefault(userDto.getId(), BigDecimal.ZERO);
        BigDecimal monthlyIncomeOfUser = avgFixedSalaryIncomeByUser.add(avgFixedNonSalaryIncomeByUser).add(varyingIncomeByUser);
        BigDecimal expenseSpentByUser = avgFixedExpenseByUser.add(varyingExpenseByUser);
        BigDecimal goalAmountOfUser = getGoalAmountOfUser(userDto.getId());
        BigDecimal estimatedIncomeByUser = estimatedIncomeOfUser.get("estimatedIncome").getOrDefault(userDto.getId(), BigDecimal.ZERO);
        peerUserDto.setAge(userDto.getAge());
        peerUserDto.setGender(userDto.getGender());
        peerUserDto.setTier(userDto.getTier());
        peerUserDto.setAverageMonthlyIncome(monthlyIncomeOfUser);
        peerUserDto.setAverageFixedSalaryIncome(avgFixedSalaryIncomeByUser);
        peerUserDto.setAverageFixedNonSalaryIncome(avgFixedNonSalaryIncomeByUser);
        peerUserDto.setAverageVaryingIncome(varyingIncomeByUser);
        peerUserDto.setAverageFixedExpenses(avgFixedExpenseByUser);
        peerUserDto.setAverageVariableExpenses(varyingExpenseByUser);
        peerUserDto.setGoalSaveAmount(goalAmountOfUser);
        peerUserDto.setAccountAverageBalance(estimatedIncomeByUser);
        peerUserDto.setFreeCashAmount(estimatedIncomeByUser.min(monthlyIncomeOfUser.subtract(expenseSpentByUser.add(goalAmountOfUser))));
    }

    /**
     * Returns the Transactions made by an User on a particular date.
     *
     * @param date - Required date.
     * @return dailyTransactionResponseDto - Details of the Transactions which are made on the requested date.
     */
    public DailyTransactionResponseDto getTransactionOnDate(LocalDate date) {
        Specification<Transaction> transactionSpecification = specifications.findTransactionByDate(date, CacheDataUtil.getUserId());
        List<TransactionDto> transactions = this.findAll(transactionSpecification);
        List<TransactionDailyOverviewDto> transactionDailyOverviewDto = new ArrayList<>();
        Map<String, BigDecimal> expenseAndIncome = new HashMap<>();
        Specification<Saving> savingSpecification = specifications.findSavingRecordsByDate(date, CacheDataUtil.getUserId());
        List<SavingDto> savings = savingService.findAll(savingSpecification);
        if (CollectionUtils.isEmpty(savings)) {
            savings = Collections.emptyList();
        }
        if (!CollectionUtils.isEmpty(transactions)) {
            Set<Long> transactionInSavings = savings.stream().map(SavingDto::getTransactionId).collect(Collectors.toSet());
            List<Long> commonTransactions = transactions.stream().map(TransactionDto::getId)
                    .filter(transactionInSavings::contains)
                    .collect(Collectors.toList());
            transactions.forEach(getTransactionDtoConsumer(transactionDailyOverviewDto, commonTransactions));
            expenseAndIncome = transactions.stream().filter(predicate -> Constants.FEGO_TRANSACTION_TYPES.contains(predicate.getFegoTransactionType())).collect(Collectors.groupingBy(TransactionDto::getFegoTransactionType, Collectors.mapping(TransactionDto::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        }
        BigDecimal totalSavedAmount = savings.stream().map(SavingDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, BigDecimal> autoDeductAndInstantAmount = savings.stream().filter(predicate -> predicate.getRuleName().equals(RuleType.AUTO_DEDUCT.name()) || predicate.getRuleName().equals(RuleType.INSTANT_SAVING_POP_UP.name())).collect(Collectors.groupingBy(SavingDto::getRuleName, Collectors.mapping(SavingDto::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        return getDailyTransactionResponseDto(transactionDailyOverviewDto, expenseAndIncome, totalSavedAmount, autoDeductAndInstantAmount);
    }

    /**
     * Updates the fields of DailyTransactionResponseDto from the calculated values returned by the main method.
     *
     * @param transactionDailyOverviewDtos - Required date.
     * @param expenseAndIncome             - Expense and Income made by an User on a particular date.
     * @param totalSavedAmount             - Savings made by an User on a particular date.
     * @param autoDeductAndInstantAmount   - Sum of Auto Deducted and Instant saving amount for a goal by user.
     * @return dailyTransactionResponseDto - Details of the Transactions which are made on the requested date.
     */
    private DailyTransactionResponseDto getDailyTransactionResponseDto(List<TransactionDailyOverviewDto> transactionDailyOverviewDtos, Map<String, BigDecimal> expenseAndIncome, BigDecimal totalSavedAmount, Map<String, BigDecimal> autoDeductAndInstantAmount) {
        DailyTransactionResponseDto dailyTransactionResponseDto = new DailyTransactionResponseDto();
        dailyTransactionResponseDto.setTransactionDailyOverviewDto(transactionDailyOverviewDtos);
        dailyTransactionResponseDto.setAutoDeduct(autoDeductAndInstantAmount.getOrDefault(RuleType.AUTO_DEDUCT.name(), BigDecimal.ZERO));
        dailyTransactionResponseDto.setInstantSave(autoDeductAndInstantAmount.getOrDefault(RuleType.INSTANT_SAVING_POP_UP.name(), BigDecimal.ZERO));
        dailyTransactionResponseDto.setExpense(expenseAndIncome.getOrDefault(Constants.EXPENSE, BigDecimal.ZERO));
        dailyTransactionResponseDto.setIncome(expenseAndIncome.getOrDefault(Constants.INCOME, BigDecimal.ZERO));
        dailyTransactionResponseDto.setTotalSaved(totalSavedAmount);
        return dailyTransactionResponseDto;
    }

    /**
     * Updates the fields of TransactionDailyOverviewDto from the calculated values returned by the main method.
     *
     * @param transactionDailyOverviewDtos - List of TransactionDailyOverviewDto which holds the details of a Transaction.
     * @param commonTransactions           - Common transaction id between Saving table and Transaction table.
     * @return Consumer<TransactionDto>    - Functional Interface which returns no result.
     */
    private Consumer<TransactionDto> getTransactionDtoConsumer(List<TransactionDailyOverviewDto> transactionDailyOverviewDtos, List<Long> commonTransactions) {
        return t -> {
            TransactionDailyOverviewDto transactionDailyOverviewDto = new TransactionDailyOverviewDto();
            transactionDailyOverviewDto.setId(t.getId());
            Instant instant = Instant.parse(t.getTransactionTimestamp());
            transactionDailyOverviewDto.setDateAndTime(Constants.formatter.format(instant));
            transactionDailyOverviewDto.setMerchant(t.getMerchant());
            transactionDailyOverviewDto.setAmount(t.getAmount());
            transactionDailyOverviewDto.setCategory(t.getFegoCategory());
            transactionDailyOverviewDto.setBank(t.getBankName());
            transactionDailyOverviewDto.setMaskedProductNumber(t.getMaskedAccountNumber());
            transactionDailyOverviewDto.setSavedAmount(commonTransactions.contains(t.getId()) ? AmountRoundUpUtil.roundUpAmount(t.getAmount(), BigDecimal.TEN) : BigDecimal.ZERO);
            transactionDailyOverviewDtos.add(transactionDailyOverviewDto);
        };
    }

    /**
     * Updates the Category of a Transaction and marks the Transaction as User-Updated.
     *
     * @param id                           - Id of the particular transaction which needs to be updated.
     * @param transactionCategoryUpdateDto - Category which needs to be updated.
     * @return consentResponseDto - Success message if the category has been updated properly.
     */
    public SuccessResponseDto updateCategory(long id, TransactionCategoryUpdateDto transactionCategoryUpdateDto) {
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        TransactionDto transactionDto = this.findById(id);
        transactionDto.setFegoCategory(transactionCategoryUpdateDto.getCategory());
        transactionDto.setIsUserUpdated(Boolean.TRUE);
        this.patch(transactionDto);
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validatePatch(TransactionDto incomingDto) {
        incomingDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPatch(Transaction incomingTransaction, Transaction toUpdateTransaction) {
        if (Objects.nonNull(incomingTransaction.getFegoCategory())) {
            toUpdateTransaction.setFegoCategory(incomingTransaction.getFegoCategory());
        }
        if (Objects.nonNull(incomingTransaction.getIsUserUpdated())) {
            toUpdateTransaction.setIsUserUpdated(incomingTransaction.getIsUserUpdated());
        }
    }

    /**
     * Retrieves transactions from OneMoney on a frequency basis.
     *
     * @return String - Success message if the transaction has been updated properly.
     */
    public SuccessResponseDto transactionSync() {
        List<TransactionSyncDto> transactionSyncDto = transactionRepository.getConsentIdForTxnSync();
        transactionSyncDto.forEach(c -> {
            logger.info("Started processing the Consent Id - {}", c.getConsentId());
            Specification<Consent> consentSpecification = specifications.findByConsentId(c.getConsentId());
            ConsentDataDto dto = consentDataService.findOne(consentSpecification);
            FiDataRangeDto fiDataRangeDto = new FiDataRangeDto();
            fiDataRangeDto.setFrom(LocalDate.parse(dto.getFiDataTo().substring(0, dto.getFiDataTo().indexOf("T"))).plusDays(1) + Constants.START_OF_DAY);
            fiDataRangeDto.setTo(LocalDate.now().minusDays(1) + Constants.END_OF_DAY);
            ConsentSyncRequestDto consentSyncRequestDto = new ConsentSyncRequestDto();
            consentSyncRequestDto.setConsentId(c.getConsentId());
            consentSyncRequestDto.setFiDataRangeDto(fiDataRangeDto);
            ConsentTransactionResponseDto consentTransactionResponseDto = restTemplateUtil
                    .postForEntity(ConsentTransactionResponseDto.class, propertyConfig.getIntegration().get(Constants.BASE_URL)
                            + propertyConfig.getIntegration().get("transaction-daily-sync"), consentSyncRequestDto);
            logger.info("Response from Integration Service - {}", consentTransactionResponseDto.getConsentResponseDto().getStatus());
            updateConsentAndUser(dto, fiDataRangeDto, consentTransactionResponseDto);
        });
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Updates Consent and User after retrieving transaction from OneMoney.
     *
     * @param consentTransactionResponseDto - Transactions returned from Integration Service.
     * @param dto                           - Consent Dto in which details has to be updated.
     * @param fiDataRangeDto                - Dto which contains the From and To range of the transaction.
     */
    private void updateConsentAndUser(ConsentDataDto dto, FiDataRangeDto fiDataRangeDto, ConsentTransactionResponseDto consentTransactionResponseDto) {
        ConsentArtefactDataDto artefactData = consentTransactionResponseDto.getConsentArtefactData();
        ConsentDataDto consentDataDto = new ConsentDataDto();
        validatePreAdd(consentDataDto, artefactData.getCustomerId(), artefactData, fiDataRangeDto.getFrom(), fiDataRangeDto.getTo());
        consentDataDto.setId(dto.getId());
        consentDataService.patch(consentDataDto);
        logger.info("Consent Details has been updated");
        save(consentTransactionResponseDto.getTransactionData(), "Batch");
        SuccessResponseDto response = restTemplateUtil
                .putForEntity(SuccessResponseDto.class, propertyConfig.getUser().get(Constants.BASE_URL)
                        + propertyConfig.getUser().get("user") + "/" + dto.getUserId(), consentTransactionResponseDto.getTransactionData().get(0).getProfile().getHoldersDto());
        logger.info("Response from User Service - {}", response.getStatus());
    }

    /**
     * Updates Consent and User after retrieving transaction from OneMoney.
     *
     * @param consentDataDto - Dto in which consent details has to be updated.
     * @param customerVua    - Customer Vua returned from OneMoney.
     * @param artefactData   - Dto which holds the details of Consent.
     * @param from           - Date from which the transaction has been pulled from OneMoney.
     * @param to             - To range up to which transaction has been pulled from OneMoney.
     */
    private void validatePreAdd(ConsentDataDto consentDataDto, String customerVua, ConsentArtefactDataDto artefactData, String from, String to) {
        consentDataDto.setConsentId(artefactData.getConsentId());
        consentDataDto.setCustomerVua(customerVua);
        consentDataDto.setConsentStart(artefactData.getConsentStart());
        consentDataDto.setConsentExpiry(artefactData.getConsentExpiry());
        consentDataDto.setFrequencyUnit(artefactData.getFrequencyUnit());
        consentDataDto.setFrequencyValue(artefactData.getFrequencyValue());
        consentDataDto.setFiDataFrom(from);
        consentDataDto.setFiDataTo(to);
    }

    /**
     * Outputs the list of category based on the active Categorization Partner (Like 3loq, finbit).
     *
     * @return - List of Fego Categories
     */
    public List<String> getFegoCategories() {
        Specification<Category> categorySpecification = specifications.findCategories(propertyConfig.getCategorizationPartner()).and(specifications.notDeleted());
        List<Category> categories = categoryRepository.findAll(categorySpecification);
        return categories.stream().map(Category::getFegoCategory).distinct().collect(Collectors.toList());
    }

    /**
     * Calculates Financial Calendar data for particular user.
     *
     * @param userId - The id of particular user for which Financial Calendar data has to be calculated.
     * @return String - Success message if data is calculated properly.
     */
    public SuccessResponseDto calculateFinancialCalendarPerUser(long userId) {
        return loadFinancialCalendarData(getAllUsers().stream().map(UserDetailsDto::getId).filter(id -> id == userId).collect(Collectors.toList()));
    }

    /**
     * Calculates Financial Calendar data for all users (Delete and Inserts newly calculated values).
     *
     * @return String - Success message if data is calculated properly.
     */
    public SuccessResponseDto calculateFinancialCalendarForAllUser() {
        financialCalendarRepository.deleteFinancialCalendar();
        return loadFinancialCalendarData(getAllUsers().stream().map(UserDetailsDto::getId).collect(Collectors.toList()));
    }

    /**
     * Calculates the Financial Calendar data for the List of Users.
     *
     * @param users - List of users.
     * @return String - Success message if data is inserted properly.
     */
    public SuccessResponseDto loadFinancialCalendarData(List<Long> users) {
        List<TransactionPerCategoryDto> transactionPerCategoryDto = transactionRepository.getTxnPerCategory(LocalDate.now().minusYears(1), LocalDate.now(), users);
        Set<String> fegoCategories = transactionPerCategoryDto.stream().map(TransactionPerCategoryDto::getFegoCategory).collect(Collectors.toSet());
        Set<String> fegoSubCategories = transactionPerCategoryDto.stream().map(TransactionPerCategoryDto::getFegoSubCategory).collect(Collectors.toSet());
        Set<String> transactionTypes = transactionPerCategoryDto.stream().map(TransactionPerCategoryDto::getType).collect(Collectors.toSet());
        List<RecurringTransactionAmountDto> amountOfRecurringTransaction = transactionRepository.getAmountOfRecurringTransactions(LocalDate.now().minusYears(1), LocalDate.now(), users, fegoCategories, fegoSubCategories, transactionTypes);
        List<EarlyDateRangePaymentsDto> earlyDateRangePaymentsDto = new ArrayList<>();
        findOverLappingDays(earlyDateRangePaymentsDto, amountOfRecurringTransaction);
        List<EarlyDateRangePaymentsDto> maxOverLappedPayments = getMaxOverLappedPayments(earlyDateRangePaymentsDto);
        List<EarlyDateRangePaymentsDto> finalOverLappedRecords = getOverLappedRecords(earlyDateRangePaymentsDto, maxOverLappedPayments);
        List<FinancialCalendarDto> currentMonthRecords = saveCurrentMonthRecords(transactionPerCategoryDto, finalOverLappedRecords);
        saveNextMonthRecords(currentMonthRecords);
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Finds the overlapping days
     *
     * @param earlyDateRangePaymentsDto    - Target list which contains the overlapping days of each transaction made by an user on a particular category.
     * @param amountOfRecurringTransaction - List of Recurring Transactions of an user.
     */
    private void findOverLappingDays(List<EarlyDateRangePaymentsDto> earlyDateRangePaymentsDto, List<RecurringTransactionAmountDto> amountOfRecurringTransaction) {
        for (RecurringTransactionAmountDto recurringTransactionAmountDto : amountOfRecurringTransaction) {
            List<Integer> values = new ArrayList<>();
            List<Integer> overLappingDays = OverLappingDaysUtil.getOverlappingDays(recurringTransactionAmountDto.getDayNumber());
            List<Integer> dayNumberOfSubCategory = amountOfRecurringTransaction.stream().filter(p -> p.getFegoSubCategory().equals(recurringTransactionAmountDto.getFegoSubCategory()) && p.getUserId().equals(recurringTransactionAmountDto.getUserId())).map(RecurringTransactionAmountDto::getDayNumber).collect(Collectors.toList());
            dayNumberOfSubCategory.forEach(d -> {
                if (overLappingDays.contains(d)) {
                    values.add(d);
                }
            });
            Collections.sort(values);
            setEarlyDateRangePayments(earlyDateRangePaymentsDto, amountOfRecurringTransaction, recurringTransactionAmountDto, values);
        }
    }

    /**
     * Finds the overlapping days
     *
     * @param earlyDateRangePaymentsDto     - Target list which contains the overlapping days of each transaction made by an user on a particular category.
     * @param amountOfRecurringTransaction  - List of Recurring Transactions of an user.
     * @param recurringTransactionAmountDto - Object of RecurringTransactionAmountDto from the list.
     * @param values                        - Overlapping days of the transaction.
     */
    private void setEarlyDateRangePayments(List<EarlyDateRangePaymentsDto> earlyDateRangePaymentsDto, List<RecurringTransactionAmountDto> amountOfRecurringTransaction, RecurringTransactionAmountDto recurringTransactionAmountDto, List<Integer> values) {
        if (values.size() >= 3) {
            EarlyDateRangePaymentsDto rangePaymentsDto = new EarlyDateRangePaymentsDto();
            rangePaymentsDto.setUserId(recurringTransactionAmountDto.getUserId());
            rangePaymentsDto.setFegoTransactionType(recurringTransactionAmountDto.getFegoTransactionType());
            rangePaymentsDto.setFegoCategory(recurringTransactionAmountDto.getFegoCategory());
            rangePaymentsDto.setFegoSubCategory(recurringTransactionAmountDto.getFegoSubCategory());
            List<BigDecimal> averageAmountOfOverlap = new ArrayList<>();
            amountOfRecurringTransaction.stream().filter(p -> p.getFegoSubCategory().equals(recurringTransactionAmountDto.getFegoSubCategory()) && p.getUserId().equals(recurringTransactionAmountDto.getUserId())).forEach(d -> {
                if (values.contains(d.getDayNumber())) {
                    averageAmountOfOverlap.add(amountOfRecurringTransaction.stream().filter(p -> p.getFegoSubCategory().equals(recurringTransactionAmountDto.getFegoSubCategory()) && p.getUserId().equals(recurringTransactionAmountDto.getUserId()) && p.getDayNumber().equals(d.getDayNumber())).map(RecurringTransactionAmountDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
                }
            });
            rangePaymentsDto.setAmount(averageAmountOfOverlap.stream().reduce(BigDecimal.ZERO, BigDecimal::add).divide(new BigDecimal(averageAmountOfOverlap.size()), 2, RoundingMode.HALF_UP));
            rangePaymentsDto.setValueDate(recurringTransactionAmountDto.getValueDate());
            rangePaymentsDto.setDayNumber(recurringTransactionAmountDto.getDayNumber());
            rangePaymentsDto.setOverLapDayNumber(values);
            values.remove(values.indexOf(recurringTransactionAmountDto.getDayNumber()));
            List<Integer> position = new ArrayList<>();
            values.forEach(v -> position.add(values.indexOf(v) + 1));
            rangePaymentsDto.setGetOverLapDayNumberPosition(Collections.max(position));
            earlyDateRangePaymentsDto.add(rangePaymentsDto);
        }
    }

    /**
     * Once finding the expense and income of an user for the current month, insert the same set of records by adding one month to start date range.
     *
     * @param currentMonthRecords - List of current months records which needs to be saved.
     */
    private void saveNextMonthRecords(List<FinancialCalendarDto> currentMonthRecords) {
        currentMonthRecords.forEach(l -> {
            l.setProposedTransactionDate(l.getStartDateRange().plusMonths(1));
            l.setTransactionFlag("PENDING");
            l.setStartDateRange(l.getStartDateRange().plusMonths(1));
            l.setEndDateRange(l.getEndDateRange().plusMonths(1));
        });
        currentMonthRecords.forEach(financialCalendarService::add);
    }

    /**
     * Inserts the expense and income of an user for the current month.
     *
     * @param transactionPerCategoryDto - Amount spent by an user for a category.
     * @param finalOverLappedRecords    - Overlapped records formed after calculation.
     * @return List<FinancialCalendarDto> - List of current month records used for inserting next month records.
     */
    private List<FinancialCalendarDto> saveCurrentMonthRecords(List<TransactionPerCategoryDto> transactionPerCategoryDto, List<EarlyDateRangePaymentsDto> finalOverLappedRecords) {
        List<FinancialCalendarDto> currentMonthRecords = new ArrayList<>();
        for (EarlyDateRangePaymentsDto finalOverLappedRecord : finalOverLappedRecords) {
            int minDayNumber = Collections.min(finalOverLappedRecord.getOverLapDayNumber());
            int maxDayNumber = Collections.min(finalOverLappedRecord.getOverLapDayNumber());
            int diffOfMinAndMax = maxDayNumber - minDayNumber;
            int monthValue = LocalDate.now().getMonthValue();
            int yearValue = LocalDate.now().getYear();
            LocalDate startRange;
            LocalDate endRange;
            if (diffOfMinAndMax <= 10) {
                startRange = getCalculatedDate(yearValue, monthValue, minDayNumber);
            } else {
                startRange = getCalculatedDate(yearValue, monthValue - 1, minDayNumber);
            }
            endRange = getCalculatedDate(yearValue, monthValue, maxDayNumber);
            FinancialCalendarDto financialCalendarDto = getFinancialCalendarDto(transactionPerCategoryDto, finalOverLappedRecord, startRange, endRange);
            currentMonthRecords.add(financialCalendarDto);
        }
        currentMonthRecords.forEach(financialCalendarService::add);
        return currentMonthRecords;
    }

    /**
     * Updates all the calculated values of Financial Calendar data.
     *
     * @param transactionPerCategoryDto - Amount spent by an user for a category.
     * @param finalOverLappedRecord     - Overlapped records formed after calculation.
     * @param startRange                - Start date taken for calculation.
     * @param endRange                  - End date taken for calculation.
     * @return FinancialCalendarDto - updated current month record.
     */
    private FinancialCalendarDto getFinancialCalendarDto(List<TransactionPerCategoryDto> transactionPerCategoryDto, EarlyDateRangePaymentsDto finalOverLappedRecord, LocalDate startRange, LocalDate endRange) {
        FinancialCalendarDto financialCalendarDto = new FinancialCalendarDto();
        financialCalendarDto.setUserId(finalOverLappedRecord.getUserId());
        financialCalendarDto.setStartDateRange(startRange);
        financialCalendarDto.setEndDateRange(endRange);
        financialCalendarDto.setTransactionType(finalOverLappedRecord.getFegoTransactionType());
        financialCalendarDto.setFegoCategory(finalOverLappedRecord.getFegoCategory());
        financialCalendarDto.setFegoSubCategory(finalOverLappedRecord.getFegoSubCategory());
        financialCalendarDto.setAmount(finalOverLappedRecord.getAmount());
        setTransactionFlag(transactionPerCategoryDto, finalOverLappedRecord, financialCalendarDto, startRange, endRange);
        getProposedTransactionDate(financialCalendarDto, startRange, endRange);
        return financialCalendarDto;
    }

    /**
     * Based on the start date and end range, proposed transaction date is calculated.
     *
     * @param financialCalendarDto - Target DTO which contains the calculated value.
     * @param startRange           - Start date taken for calculation.
     * @param endRange             - End date taken for calculation.
     */
    private void getProposedTransactionDate(FinancialCalendarDto financialCalendarDto, LocalDate startRange, LocalDate endRange) {
        if (startRange.isAfter(LocalDate.now())) {
            financialCalendarDto.setProposedTransactionDate(startRange);
        } else if (startRange.compareTo(LocalDate.now()) <= 0 && LocalDate.now().isBefore(endRange)) {
            financialCalendarDto.setProposedTransactionDate(LocalDate.now());
        } else if (LocalDate.now().isAfter(startRange) && LocalDate.now().compareTo(endRange) >= 0) {
            financialCalendarDto.setProposedTransactionDate(endRange);
        }
    }

    /**
     * Based on the start date and end range, Transaction flag is set.
     *
     * @param financialCalendarDto      - Target DTO which contains the calculated value.
     * @param transactionPerCategoryDto - Target DTO which contains the overlapping days of each transaction made by an user on a particular category.
     * @param finalOverLappedRecord     - Overlapped records formed after calculation.
     * @param startRange                - Start date taken for calculation.
     * @param endRange                  - End date taken for calculation.
     */
    private void setTransactionFlag(List<TransactionPerCategoryDto> transactionPerCategoryDto, EarlyDateRangePaymentsDto finalOverLappedRecord, FinancialCalendarDto financialCalendarDto, LocalDate startRange, LocalDate endRange) {
        Optional<LocalDate> maxValueDate = transactionPerCategoryDto.stream().filter(t -> t.getUserId() == finalOverLappedRecord.getUserId() && t.getFegoTransactionType().equals(finalOverLappedRecord.getFegoTransactionType()) && t.getFegoCategory().equals(finalOverLappedRecord.getFegoCategory()) && t.getFegoSubCategory().equals(finalOverLappedRecord.getFegoSubCategory())).map(TransactionPerCategoryDto::getValueDate).findFirst();
        if (maxValueDate.isPresent()) {
            LocalDate latestTransactionDate = maxValueDate.get();
            financialCalendarDto.setActualTransactionDate(latestTransactionDate);
            if (latestTransactionDate.compareTo(startRange) >= 0 && latestTransactionDate.compareTo(endRange) <= 0) {
                financialCalendarDto.setTransactionFlag("COMPLETE");
            } else if (LocalDate.now().isAfter(endRange) && latestTransactionDate.isBefore(startRange)) {
                financialCalendarDto.setTransactionFlag("MISSED");
            } else if (LocalDate.now().isAfter(endRange) && latestTransactionDate.isAfter(endRange)) {
                financialCalendarDto.setTransactionFlag("LATE");
            } else {
                financialCalendarDto.setTransactionFlag("PENDING");
            }
        }
    }

    /**
     * Find the records with maximum Overlapped days.
     *
     * @param earlyDateRangePaymentsDto - Target list which contains the overlapping days of each transaction made by an user on a particular category.
     */
    private List<EarlyDateRangePaymentsDto> getMaxOverLappedPayments(List<EarlyDateRangePaymentsDto> earlyDateRangePaymentsDto) {
        List<EarlyDateRangePaymentsDto> maxOverLappedPayments = new ArrayList<>();
        for (EarlyDateRangePaymentsDto rangePaymentsDto : earlyDateRangePaymentsDto) {
            long size = maxOverLappedPayments.stream().filter(o -> o.getFegoSubCategory().equals(rangePaymentsDto.getFegoSubCategory()) && o.getUserId() == rangePaymentsDto.getUserId()).count();
            findRecordsWithOverlap(earlyDateRangePaymentsDto, maxOverLappedPayments, rangePaymentsDto, size);
        }
        return maxOverLappedPayments;
    }

    /**
     * Find the common records which has common overlapping days.
     *
     * @param earlyDateRangePaymentsDto - Target list which contains the overlapping days of each transaction made by an user on a particular category.
     * @param maxOverLappedPayments     - Maximum number of overlapping record.
     * @param rangePaymentsDto          - Object of EarlyDateRangePaymentsDto from the list.
     * @param size                      - Count of records which equals fego subcategory and the user id.
     */
    private void findRecordsWithOverlap(List<EarlyDateRangePaymentsDto> earlyDateRangePaymentsDto, List<EarlyDateRangePaymentsDto> maxOverLappedPayments, EarlyDateRangePaymentsDto rangePaymentsDto, long size) {
        if (size == 0) {
            List<EarlyDateRangePaymentsDto> filteredDto = earlyDateRangePaymentsDto.stream().filter(e -> e.getFegoSubCategory().equals(rangePaymentsDto.getFegoSubCategory()) && e.getUserId() == rangePaymentsDto.getUserId()).collect(Collectors.toList());
            int maximumValue = filteredDto.stream().map(mapper -> mapper.getOverLapDayNumber().size()).max(Integer::compare).orElse(0);
            long count = filteredDto.stream().filter(i -> i.getOverLapDayNumber().size() == maximumValue).count();
            if (count >= 2) {
                List<EarlyDateRangePaymentsDto> maxOverLapped = filteredDto.stream().filter(i -> i.getOverLapDayNumber().size() == maximumValue).collect(Collectors.toList());
                List<EarlyDateRangePaymentsDto> commonRecords = new ArrayList<>();
                for (int i = 0; i < maxOverLapped.size() - 1; i++) {
                    for (int j = i + 1; j < maxOverLapped.size(); j++) {
                        findRecordsWithoutOverlap(maxOverLapped, commonRecords, i, j);
                    }
                }
                mergeCommonRecords(maxOverLappedPayments, maxOverLapped, commonRecords);
            } else {
                maxOverLappedPayments.addAll(filteredDto.stream().filter(i -> i.getOverLapDayNumber().size() == maximumValue).collect(Collectors.toList()));
            }
        }
    }

    /**
     * Find the common records which doesn't has common overlapping days.
     *
     * @param maxOverLapped - Maximum number of overlapping record.
     * @param commonRecords - Identified common records which has common overlapping days.
     * @param i             - Key of outer iteration.
     * @param j             - Key of inner iteration.
     */
    private void findRecordsWithoutOverlap(List<EarlyDateRangePaymentsDto> maxOverLapped, List<EarlyDateRangePaymentsDto> commonRecords, int i, int j) {
        if (!Collections.disjoint(maxOverLapped.get(i).getOverLapDayNumber(), maxOverLapped.get(j).getOverLapDayNumber())) {
            findRecordsWithCommonOverLap(maxOverLapped, commonRecords, i, j);
        } else {
            long nonOverlappingRecordOfI = commonRecords.stream().filter(o -> o.getFegoSubCategory().equals(maxOverLapped.get(i).getFegoSubCategory()) && o.getUserId() == maxOverLapped.get(i).getUserId()).count();
            if (nonOverlappingRecordOfI == 0) {
                commonRecords.add(maxOverLapped.get(i));
            }
            long nonOverlappingRecordOfJ = commonRecords.stream().filter(o -> o.getFegoSubCategory().equals(maxOverLapped.get(j).getFegoSubCategory()) && o.getUserId() == maxOverLapped.get(j).getUserId()).count();
            if (nonOverlappingRecordOfJ == 0) {
                commonRecords.add(maxOverLapped.get(j));
            }
        }
    }

    /**
     * Merge the common and uncommon records with respect to Overlapped days in common.
     *
     * @param maxOverLapped         - Maximum number of overlapping record.
     * @param commonRecords         - Identified common records which has common overlapping days.
     * @param maxOverLappedPayments - List of maximum overlapped records.
     */
    private void mergeCommonRecords(List<EarlyDateRangePaymentsDto> maxOverLappedPayments, List<EarlyDateRangePaymentsDto> maxOverLapped, List<EarlyDateRangePaymentsDto> commonRecords) {
        if (commonRecords.size() > 1) {
            int minimumValue = commonRecords.stream().map(EarlyDateRangePaymentsDto::getGetOverLapDayNumberPosition).min(Integer::compare).orElse(0);
            Optional<EarlyDateRangePaymentsDto> finalOverLappedRecord = commonRecords.stream().filter(i -> i.getOverLapDayNumber().size() == minimumValue).findFirst();
            if (finalOverLappedRecord.isPresent()) {
                commonRecords.add(finalOverLappedRecord.get());
                maxOverLapped.forEach(m -> {
                    if (Collections.disjoint(m.getOverLapDayNumber(), finalOverLappedRecord.get().getOverLapDayNumber())) {
                        commonRecords.add(m);
                    }
                });
                maxOverLappedPayments.addAll(commonRecords);
            }
        } else {
            maxOverLappedPayments.addAll(commonRecords);
        }
    }

    /**
     * Find the common records which has common overlapping days.
     *
     * @param maxOverLapped - Maximum number of overlapping record.
     * @param commonRecords - Identified common records which has common overlapping days.
     * @param i             - Key of outer iteration.
     * @param j             - Key of inner iteration.
     */
    private void findRecordsWithCommonOverLap(List<EarlyDateRangePaymentsDto> maxOverLapped, List<EarlyDateRangePaymentsDto> commonRecords, int i, int j) {
        Optional<EarlyDateRangePaymentsDto> paymentsDto;
        if (maxOverLapped.get(i).getGetOverLapDayNumberPosition().equals(maxOverLapped.get(j).getGetOverLapDayNumberPosition())) {
            int min = Integer.min(maxOverLapped.get(i).getDayNumber(), maxOverLapped.get(j).getDayNumber());
            paymentsDto = maxOverLapped.stream().filter(r -> r.getDayNumber() == min).findFirst();
        } else {
            int min = Integer.min(maxOverLapped.get(i).getGetOverLapDayNumberPosition(), maxOverLapped.get(j).getGetOverLapDayNumberPosition());
            paymentsDto = maxOverLapped.stream().filter(r -> r.getGetOverLapDayNumberPosition() == min).findFirst();
        }
        if (paymentsDto.isPresent()) {
            long existingCount = commonRecords.stream().filter(o -> o.getFegoSubCategory().equals(paymentsDto.get().getFegoSubCategory()) && o.getUserId() == paymentsDto.get().getUserId()).count();
            if (existingCount == 0) {
                commonRecords.add(paymentsDto.get());
            }
        }
    }

    /**
     * Find the common records which has both common in overlapping records.
     *
     * @param maxOverLappedPayments     - Maximum number of overlapping record.
     * @param earlyDateRangePaymentsDto - Target list which contains the overlapping days of each transaction made by an user on a particular category.
     */
    private List<EarlyDateRangePaymentsDto> getOverLappedRecords(List<EarlyDateRangePaymentsDto> earlyDateRangePaymentsDto, List<EarlyDateRangePaymentsDto> maxOverLappedPayments) {
        List<EarlyDateRangePaymentsDto> finalOverLappedRecords = new ArrayList<>();
        for (EarlyDateRangePaymentsDto maxOverLappedPayment : maxOverLappedPayments) {
            initialSetUpOfDto(earlyDateRangePaymentsDto, maxOverLappedPayments, finalOverLappedRecords, maxOverLappedPayment);
        }
        return finalOverLappedRecords;
    }

    /**
     * Find the records which has common overlap days and update finalOverlappingdays.
     *
     * @param maxOverLappedPayments     - Maximum number of overlapping record.
     * @param earlyDateRangePaymentsDto - Target list which contains the overlapping days of each transaction made by an user on a particular category.
     * @param finalOverLappedRecords    - Overlapped records.
     * @param maxOverLappedPayment      - Maximum number of Overlapping record.
     */
    private void initialSetUpOfDto(List<EarlyDateRangePaymentsDto> earlyDateRangePaymentsDto, List<EarlyDateRangePaymentsDto> maxOverLappedPayments, List<EarlyDateRangePaymentsDto> finalOverLappedRecords, EarlyDateRangePaymentsDto maxOverLappedPayment) {
        if (maxOverLappedPayment.getOverLapDayNumber().size() >= 4) {
            long singleOverlappingRecordCount = maxOverLappedPayments.stream().filter(n -> n.getFegoSubCategory().equals(maxOverLappedPayment.getFegoSubCategory()) && n.getUserId() == maxOverLappedPayment.getUserId()).count();
            if (singleOverlappingRecordCount == 1) {
                List<EarlyDateRangePaymentsDto> values = earlyDateRangePaymentsDto.stream().filter(s -> s.getUserId() == maxOverLappedPayment.getUserId() && s.getFegoSubCategory().equals(maxOverLappedPayment.getFegoSubCategory())).collect(Collectors.toList());
                List<EarlyDateRangePaymentsDto> disjointRecords = new ArrayList<>();
                values.forEach(r -> {
                    if (r.getOverLapDayNumber().size() < maxOverLappedPayment.getOverLapDayNumber().size() && !Collections.disjoint(r.getOverLapDayNumber(), maxOverLappedPayment.getOverLapDayNumber())) {
                        disjointRecords.add(r);
                    }
                });
                int minimumValue = disjointRecords.stream().map(EarlyDateRangePaymentsDto::getGetOverLapDayNumberPosition).min(Integer::compare).orElse(0);
                Optional<EarlyDateRangePaymentsDto> finalOverLappedRecord = disjointRecords.stream().filter(i -> i.getOverLapDayNumber().size() == minimumValue).findFirst();
                finalOverLappedRecord.ifPresent(finalOverLappedRecords::add);
            } else {
                finalOverLappedRecords.add(maxOverLappedPayment);
            }
        } else {
            finalOverLappedRecords.add(maxOverLappedPayment);
        }
    }

    /**
     * Method to handle the date parsing from day number to LocalDate.
     *
     * @param yearValue  - Current year value.
     * @param monthValue - Current month value.
     * @param value      - Day which needs to be converted to LocaDate.
     * @return date - Calculated date.
     */
    private LocalDate getCalculatedDate(int yearValue, int monthValue, int value) {
        LocalDate date;
        if (LocalDate.now().lengthOfMonth() == 29 && value >= 29) {
            date = LocalDate.of(yearValue, monthValue, 29);
        } else if (LocalDate.now().lengthOfMonth() == 28 && value >= 28) {
            date = LocalDate.of(yearValue, monthValue, 28);
        } else if (LocalDate.now().lengthOfMonth() == 30 && value >= 30) {
            date = LocalDate.of(yearValue, monthValue, 30);
        } else if (LocalDate.now().lengthOfMonth() == 31 && value >= 31) {
            date = LocalDate.of(yearValue, monthValue, 31);
        } else {
            date = LocalDate.of(yearValue, monthValue, value);
        }
        return date;
    }
}