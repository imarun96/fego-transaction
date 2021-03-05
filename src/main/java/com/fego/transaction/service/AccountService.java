package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.config.PropertyConfig;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.utils.AmazonS3Service;
import com.fego.transaction.common.utils.CacheDataUtil;
import com.fego.transaction.dto.*;
import com.fego.transaction.dto.integration.AccountLinkRequestDto;
import com.fego.transaction.dto.integration.FipResponseDto;
import com.fego.transaction.dto.integration.HolderDto;
import com.fego.transaction.dto.integration.SummaryDto;
import com.fego.transaction.entity.Account;
import com.fego.transaction.mapper.AccountMapper;
import com.fego.transaction.repository.AccountRepository;
import com.fego.transaction.task.AccountTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class AccountService extends BaseService<Account, AccountDataDto, AccountTask> {

    private final IdSpecifications<Account> specifications;
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
    private final AmazonS3Service amazonS3Service;
    private final PropertyConfig propertyConfig;
    private final Logger logger = LoggerFactory.getLogger(AccountService.class);

    public AccountService(AccountRepository accountRepository, BaseMapper<Account, AccountDataDto> accountMapper,
                          IdSpecifications<Account> accountIdSpecifications, BaseTask<Account> accountTask, AccountMapper mapper,
                          AmazonS3Service amazonS3Service, PropertyConfig propertyConfig, IdSpecifications<Account> specifications) {
        super(accountRepository, accountMapper, accountIdSpecifications, accountTask);
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.amazonS3Service = amazonS3Service;
        this.propertyConfig = propertyConfig;
        this.specifications = specifications;
    }

    /**
     * To insert Accounts into Database
     *
     * @param userRegistrationResponseDto - List of Accounts as a response from Integration Service.
     * @return consentResponseDto         - Success message if Accounts are inserted properly.
     */
    public SuccessResponseDto saveAccount(UserRegistrationResponseDto userRegistrationResponseDto) {
        List<AccountDataDto> accountDataDto = getBankImageFromFip(userRegistrationResponseDto.getAccountDto(),
                userRegistrationResponseDto.getFipResponseDto());
        accountDataDto.forEach(this::add);
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAdd(AccountDataDto accountDataDto) {
        accountDataDto.setUserId(CacheDataUtil.getUserId());
        accountDataDto.setCreatedBy(CacheDataUtil.getUserId());
        accountDataDto.setUpdatedBy(CacheDataUtil.getUserId());
        accountDataDto.setIsAccountLinked(Boolean.FALSE);
    }

    /**
     * Save the Logo of the Bank in Amazon S3 Bucket.
     *
     * @param accountDto     - List of Accounts as a response from Integration Service.
     * @param fipResponseDto - List of Fip as a response from Integration Service.
     * @return accountDataDto - Account list with Bank logo's updated.
     */
    private List<AccountDataDto> getBankImageFromFip(List<AccountDto> accountDto, FipResponseDto fipResponseDto) {
        List<AccountDataDto> accountDataDto = new ArrayList<>();
        fipResponseDto.getFip().forEach(m -> accountDto.stream().filter(predicate -> predicate.getData().getFipId().equals(m.getFipId()))
                .forEach(action -> {
                    action.getData().setType(action.getType());
                    action.getData().setBankName(m.getFipName());
                    String imageUrl = m.getLogoUrl();
                    String fileName = m.getFipId() + Constants.HYPHEN
                            + imageUrl.substring(imageUrl.lastIndexOf('/') + 1, m.getLogoUrl().length());
                    action.getData().setImage(fileName);
                    try {
                        amazonS3Service.saveFileIntoS3(propertyConfig.getAws().get("bucketName"), fileName,
                                new URL(imageUrl));
                    } catch (MalformedURLException e) {
                        logger.error("Exception has been occurred - {}", e.getMessage());
                    }
                    accountDataDto.add(action.getData());
                })
        );
        return accountDataDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validatePatch(AccountDataDto incomingDto) {
        incomingDto.setUpdatedBy(CacheDataUtil.getUserId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPatch(Account incomingAccount, Account toUpdateAccount) {
        toUpdateAccount.setConsentId(incomingAccount.getConsentId());
        if (Objects.nonNull(incomingAccount.getIsAccountLinked())) {
            toUpdateAccount.setIsAccountLinked(incomingAccount.getIsAccountLinked());
        }
        if (Objects.nonNull(incomingAccount.getLinkAccountReferenceNumber())) {
            toUpdateAccount.setLinkAccountReferenceNumber(incomingAccount.getLinkAccountReferenceNumber());
        }
        if (Objects.nonNull(incomingAccount.getAccountType())) {
            toUpdateAccount.setAccountType(incomingAccount.getAccountType());
        }
        if (Objects.nonNull(incomingAccount.getProductType())) {
            toUpdateAccount.setProductType(incomingAccount.getProductType());
        }
        if (Objects.nonNull(incomingAccount.getCurrentOdLimit())) {
            toUpdateAccount.setCurrentOdLimit(incomingAccount.getCurrentOdLimit());
        }
        if (Objects.nonNull(incomingAccount.getCurrency())) {
            toUpdateAccount.setCurrency(incomingAccount.getCurrency());
        }
        if (Objects.nonNull(incomingAccount.getBranch())) {
            toUpdateAccount.setBranch(incomingAccount.getBranch());
        }
        if (Objects.nonNull(incomingAccount.getIfscCode())) {
            toUpdateAccount.setIfscCode(incomingAccount.getIfscCode());
        }
        if (Objects.nonNull(incomingAccount.getFacility())) {
            toUpdateAccount.setFacility(incomingAccount.getFacility());
        }
        continuePatch(incomingAccount, toUpdateAccount);
    }

    /**
     * {@inheritDoc}
     */
    public void continuePatch(Account incomingAccount, Account toUpdateAccount) {
        if (Objects.nonNull(incomingAccount.getOpeningDate())) {
            toUpdateAccount.setOpeningDate(incomingAccount.getOpeningDate());
        }
        if (Objects.nonNull(incomingAccount.getMicrCode())) {
            toUpdateAccount.setMicrCode(incomingAccount.getMicrCode());
        }
        if (Objects.nonNull(incomingAccount.getFullName())) {
            toUpdateAccount.setFullName(incomingAccount.getFullName());
        }
        if (Objects.nonNull(incomingAccount.getNominee())) {
            toUpdateAccount.setNominee(incomingAccount.getNominee());
        }
        if (Objects.nonNull(incomingAccount.getDrawingLimit())) {
            toUpdateAccount.setDrawingLimit(incomingAccount.getDrawingLimit());
        }
        if (Objects.nonNull(incomingAccount.getStatus())) {
            toUpdateAccount.setStatus(incomingAccount.getStatus());
        }
        if (Objects.nonNull(incomingAccount.getCurrentBalance())) {
            toUpdateAccount.setCurrentBalance(incomingAccount.getCurrentBalance());
        }
        if (Objects.nonNull(incomingAccount.getCreditLimit())) {
            toUpdateAccount.setCreditLimit(incomingAccount.getCreditLimit());
        }
        if (Objects.nonNull(incomingAccount.getAvailableCreditLimit())) {
            toUpdateAccount.setAvailableCreditLimit(incomingAccount.getAvailableCreditLimit());
        }
        if (Objects.nonNull(incomingAccount.getCurrentDue())) {
            toUpdateAccount.setCurrentDue(incomingAccount.getCurrentDue());
        }
        if (Objects.nonNull(incomingAccount.getDueDate())) {
            toUpdateAccount.setDueDate(incomingAccount.getDueDate());
        }
    }

    /**
     * Link an account to OneMoney
     *
     * @param accountLinkRequestDto - Account which needs to be linked.
     * @return consentResponseDto - Success message if Account is linked properly.
     */
    public SuccessResponseDto linkAccount(AccountLinkRequestDto accountLinkRequestDto) {
        SuccessResponseDto successResponseDto = new SuccessResponseDto();
        Specification<Account> baseSpecification = specifications
                .findByProductNumber(accountLinkRequestDto.getAccountReferenceNumber());
        AccountDataDto dto = this.findOne(baseSpecification);
        dto.setIsAccountLinked(Boolean.TRUE);
        dto.setLinkAccountReferenceNumber(accountLinkRequestDto.getLinkReferenceNumber());
        this.patch(dto);
        successResponseDto.setStatus(Constants.SUCCESS);
        return successResponseDto;
    }

    /**
     * Update an Account after pulling transaction history of an User.
     *
     * @param linkedAccountReferenceNumber - Account Reference Number for the linked Account received from OneMoney.
     * @param type                         - Type of Account.
     * @param summary                      - Details of an Bank Account received from OneMoney.
     * @param holder                       - Details of Account Holder received from OneMoney.
     */
    public void updateAccount(String linkedAccountReferenceNumber, String type, SummaryDto summary, HolderDto holder) {
        Specification<Account> baseSpecification = specifications.findByLinkAccountReferenceNumber(linkedAccountReferenceNumber);
        AccountDataDto dto = this.findOne(baseSpecification);
        dto.setProductType(summary.getAccountType());
        dto.setAccountType(type);
        dto.setCurrentOdLimit(summary.getCurrentOdLimit());
        dto.setCurrency(summary.getCurrency());
        dto.setBranch(summary.getBranch());
        dto.setIfscCode(summary.getIfscCode());
        dto.setFacility(summary.getFacility());
        dto.setOpeningDate(summary.getOpeningDate());
        dto.setMicrCode(summary.getMicrCode());
        dto.setFullName(holder.getName());
        dto.setNominee(holder.getNominee());
        dto.setDrawingLimit(summary.getDrawingLimit());
        dto.setStatus(summary.getStatus());
        dto.setCurrentBalance(summary.getCurrentBalance());
        dto.setCreditLimit(summary.getCreditLimit());
        dto.setAvailableCreditLimit(summary.getAvailableCredit());
        dto.setCurrentDue(summary.getCurrentDue());
        dto.setDueDate(summary.getDueDate());
        this.patch(dto);
    }

    /**
     * Current balance of an User Accounts.
     *
     * @return accountBalanceResponseDto - Balance of Account Types such as Current, Saving and Credit Card.
     */
    public AccountBalanceResponseDto findAccountBalance() {
        AccountBalanceResponseDto accountBalanceResponseDto = new AccountBalanceResponseDto();
        List<AccountBalanceDto> accountBalanceDto = accountRepository.getAccountBalance(Constants.ACCOUNT_TYPES, CacheDataUtil.getUserId());
        BigDecimal totalAccountBalance = accountBalanceDto.stream().filter(predicate -> !predicate.getProductType().equals(Constants.CREDIT_CARD)).map(AccountBalanceDto::getCurrentBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCreditBalance = accountBalanceDto.stream().filter(predicate -> predicate.getProductType().equals(Constants.CREDIT_CARD)).map(AccountBalanceDto::getAvailableCreditLimit).reduce(BigDecimal.ZERO, BigDecimal::add);
        accountBalanceResponseDto.setAccountDetails(accountBalanceDto);
        accountBalanceResponseDto.setAccountBalance(totalAccountBalance);
        accountBalanceResponseDto.setCreditCardBalance(totalCreditBalance);
        return accountBalanceResponseDto;
    }

    /**
     * Details of every account which is associated to an user.
     *
     * @return List<AccountsResponseDto> - Target dto which contains account overview.
     */
    public List<AccountsResponseDto> getAccountsOfUser() {
        List<AccountsResponseDto> accountsResponseDto = new ArrayList<>();
        Specification<Account> accountSpecification = specifications.findAccounts(CacheDataUtil.getUserId());
        List<AccountDataDto> accounts = this.findAll(accountSpecification);
        accounts.forEach(a -> {
            AccountsResponseDto responseDto = new AccountsResponseDto();
            responseDto.setBankImageUrl(a.getImage());
            responseDto.setBankName(a.getBankName());
            responseDto.setAccountType(a.getProductType());
            responseDto.setMaskedProductNumber(a.getMaskedProductNumber());
            responseDto.setIsAccountLinked(a.getIsAccountLinked());
            responseDto.setType(a.getType());
            responseDto.setFipId(a.getFipId());
            responseDto.setAccountLinkReferenceNumber(a.getProductNumber());
            accountsResponseDto.add(responseDto);
        });
        return accountsResponseDto;
    }
}