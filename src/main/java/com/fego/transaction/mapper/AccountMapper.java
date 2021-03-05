package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.config.PropertyConfig;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.dto.AccountDataDto;
import com.fego.transaction.entity.Account;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class AccountMapper implements BaseMapper<Account, AccountDataDto> {

    private final PropertyConfig propertyConfig;

    public AccountMapper(PropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }

    public AccountDataDto domainToDto(Account account) {
        AccountDataDto accountDataDto = new AccountDataDto();
        accountDataDto.setAccountType(account.getAccountType());
        accountDataDto.setUserId(account.getUserId());
        accountDataDto.setProductNumber(account.getProductNumber());
        accountDataDto.setMaskedProductNumber(account.getMaskedProductNumber());
        accountDataDto.setPartnerId(account.getPartnerId());
        accountDataDto.setPartnerName(account.getPartnerName());
        accountDataDto.setPartnerType(account.getPartnerType());
        accountDataDto.setFullName(account.getFullName());
        accountDataDto.setProductType(account.getProductType());
        accountDataDto.setType(account.getType());
        accountDataDto.setNominee(account.getNominee());
        accountDataDto.setOpeningDate(account.getOpeningDate());
        accountDataDto.setCurrentOdLimit(account.getCurrentOdLimit());
        accountDataDto.setCurrentBalance(account.getCurrentBalance());
        accountDataDto.setAvailableCreditLimit(account.getAvailableCreditLimit());
        accountDataDto.setCreditLimit(account.getCreditLimit());
        accountDataDto.setDrawingLimit(account.getDrawingLimit());
        accountDataDto.setStatus(account.getStatus());
        accountDataDto.setIfscCode(account.getIfscCode());
        accountDataDto.setBranch(account.getBranch());
        accountDataDto.setMicrCode(account.getMicrCode());
        accountDataDto.setCurrency(account.getCurrency());
        accountDataDto.setFacility(account.getFacility());
        accountDataDto.setLongitude(account.getLongitude());
        accountDataDto.setLatitude(account.getLatitude());
        accountDataDto.setFipId(account.getFipId());
        accountDataDto.setLinkAccountReferenceNumber(account.getLinkAccountReferenceNumber());
        accountDataDto.setIsAccountLinked(account.getIsAccountLinked());
        accountDataDto.setUserInfo(account.getUserInfo());
        accountDataDto.setDueDate(account.getDueDate());
        accountDataDto.setCurrentDue(account.getCurrentDue());
        accountDataDto.setImage(propertyConfig.getAws().get(Constants.BUCKET_BASE_URL) + account.getImage());
        accountDataDto.setBankName(account.getBankName());
        accountDataDto.setConsentId(account.getConsentId());
        accountDataDto.setId(account.getId());
        accountDataDto.setIsDeleted(account.isDeleted());
        accountDataDto.setCreatedAt(account.getCreatedAt());
        accountDataDto.setUpdatedAt(account.getUpdatedAt());
        accountDataDto.setCreatedBy(account.getCreatedBy());
        accountDataDto.setUpdatedBy(account.getUpdatedBy());
        return accountDataDto;
    }

    public Account dtoToDomain(AccountDataDto accountDataDto) {
        Account account = new Account();
        account.setAccountType(accountDataDto.getAccountType());
        account.setUserId(accountDataDto.getUserId());
        account.setProductNumber(accountDataDto.getProductNumber());
        account.setMaskedProductNumber(accountDataDto.getMaskedProductNumber());
        account.setPartnerId(accountDataDto.getPartnerId());
        account.setPartnerName(accountDataDto.getPartnerName());
        account.setPartnerType(accountDataDto.getPartnerType());
        account.setFullName(accountDataDto.getFullName());
        account.setProductType(accountDataDto.getProductType());
        account.setType(accountDataDto.getType());
        account.setNominee(accountDataDto.getNominee());
        account.setOpeningDate(accountDataDto.getOpeningDate());
        account.setCurrentOdLimit(accountDataDto.getCurrentOdLimit());
        account.setCurrentBalance(accountDataDto.getCurrentBalance());
        account.setAvailableCreditLimit(accountDataDto.getAvailableCreditLimit());
        account.setCreditLimit(accountDataDto.getCreditLimit());
        account.setDrawingLimit(accountDataDto.getDrawingLimit());
        account.setStatus(accountDataDto.getStatus());
        account.setIfscCode(accountDataDto.getIfscCode());
        account.setBranch(accountDataDto.getBranch());
        account.setMicrCode(accountDataDto.getMicrCode());
        account.setCurrency(accountDataDto.getCurrency());
        account.setFacility(accountDataDto.getFacility());
        account.setLongitude(accountDataDto.getLongitude());
        account.setLatitude(accountDataDto.getLatitude());
        account.setFipId(accountDataDto.getFipId());
        account.setLinkAccountReferenceNumber(accountDataDto.getLinkAccountReferenceNumber());
        account.setIsAccountLinked(accountDataDto.getIsAccountLinked());
        account.setUserInfo(accountDataDto.getUserInfo());
        account.setDueDate(accountDataDto.getDueDate());
        account.setCurrentDue(accountDataDto.getCurrentDue());
        account.setImage(accountDataDto.getImage());
        account.setBankName(accountDataDto.getBankName());
        account.setConsentId(accountDataDto.getConsentId());
        account.setDeleted(accountDataDto.getIsDeleted());
        account.setCreatedBy(accountDataDto.getCreatedBy());
        account.setUpdatedBy(accountDataDto.getUpdatedBy());
        return account;
    }
}