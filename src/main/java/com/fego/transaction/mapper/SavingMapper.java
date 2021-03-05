package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.dto.SavingDto;
import com.fego.transaction.entity.Saving;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class SavingMapper implements BaseMapper<Saving, SavingDto> {

    public SavingDto domainToDto(Saving saving) {
        SavingDto savingDto = new SavingDto();
        savingDto.setGoalId(saving.getGoalId());
        savingDto.setRuleName(saving.getRuleName());
        savingDto.setTransactionDate(saving.getTransactionDate());
        savingDto.setAmount(saving.getAmount());
        savingDto.setTransactionId(saving.getTransactionId());
        savingDto.setUserId(saving.getUserId());
        savingDto.setAccountId(saving.getAccountId());
        savingDto.setMerchant(saving.getMerchant());
        savingDto.setBankName(saving.getBankName());
        savingDto.setMaskedProductNumber(saving.getMaskedProductNumber());
        savingDto.setId(saving.getId());
        savingDto.setIsDeleted(saving.isDeleted());
        savingDto.setCreatedAt(saving.getCreatedAt());
        savingDto.setUpdatedAt(saving.getUpdatedAt());
        savingDto.setCreatedBy(saving.getCreatedBy());
        savingDto.setUpdatedBy(saving.getUpdatedBy());
        return savingDto;
    }

    public Saving dtoToDomain(SavingDto savingDto) {
        Saving saving = new Saving();
        saving.setGoalId(savingDto.getGoalId());
        saving.setRuleName(savingDto.getRuleName());
        saving.setTransactionDate(savingDto.getTransactionDate());
        saving.setAmount(savingDto.getAmount());
        saving.setTransactionId(savingDto.getTransactionId());
        saving.setUserId(savingDto.getUserId());
        saving.setAccountId(savingDto.getAccountId());
        saving.setMerchant(savingDto.getMerchant());
        saving.setBankName(savingDto.getBankName());
        saving.setMaskedProductNumber(savingDto.getMaskedProductNumber());
        saving.setDeleted(savingDto.getIsDeleted());
        saving.setCreatedBy(savingDto.getCreatedBy());
        saving.setUpdatedBy(savingDto.getUpdatedBy());
        return saving;
    }
}