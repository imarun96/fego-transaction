package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.dto.PeerUserDto;
import com.fego.transaction.entity.PeerUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class PeerUserMapper implements BaseMapper<PeerUser, PeerUserDto> {

    public PeerUserDto domainToDto(PeerUser peerUser) {
        PeerUserDto peerUserDto = new PeerUserDto();
        peerUserDto.setUserId(peerUser.getUserId());
        peerUserDto.setAge(peerUser.getAge());
        peerUserDto.setGender(peerUser.getGender());
        peerUserDto.setTier(peerUser.getTier());
        peerUserDto.setAverageMonthlyIncome(peerUser.getAverageMonthlyIncome());
        peerUserDto.setNoOfTransactionsPerMonth(peerUser.getNoOfTransactionsPerMonth());
        peerUserDto.setPeerGroupId(peerUser.getPeerGroupId());
        peerUserDto.setAveragePeerGoalSavingAmount(peerUser.getAveragePeerGoalSavingAmount());
        peerUserDto.setFegoScorePercentile(peerUser.getFegoScorePercentile());
        peerUserDto.setAccountAverageBalance(peerUser.getAccountAverageBalance());
        peerUserDto.setAverageFixedSalaryIncome(peerUser.getAverageFixedSalaryIncome());
        peerUserDto.setAverageFixedNonSalaryIncome(peerUser.getAverageFixedNonSalaryIncome());
        peerUserDto.setAverageVaryingIncome(peerUser.getAverageVaryingIncome());
        peerUserDto.setAverageFixedExpenses(peerUser.getAverageFixedExpenses());
        peerUserDto.setAverageVariableExpenses(peerUser.getAverageVariableExpenses());
        peerUserDto.setPrimaryAccountId(peerUser.getPrimaryAccountId());
        peerUserDto.setGoalSaveAmount(peerUser.getGoalSaveAmount());
        peerUserDto.setFreeCashAmount(peerUser.getFreeCashAmount());
        peerUserDto.setAutoDeductFrequency(peerUser.getAutoDeductFrequency());
        peerUserDto.setInstantSaveFrequency(peerUser.getInstantSaveFrequency());
        peerUserDto.setRoundUpFrequency(peerUser.getRoundUpFrequency());
        if (peerUser.getCategories() == null || peerUser.getCategories().trim().length() == 0) {
            peerUserDto.setCategories(Collections.emptyList());
        } else {
            peerUserDto.setCategories(Arrays.asList(peerUser.getCategories().split(Constants.COMMA)));
        }
        peerUserDto.setId(peerUser.getId());
        peerUserDto.setIsDeleted(peerUser.isDeleted());
        peerUserDto.setCreatedAt(peerUser.getCreatedAt());
        peerUserDto.setUpdatedAt(peerUser.getUpdatedAt());
        peerUserDto.setCreatedBy(peerUser.getCreatedBy());
        peerUserDto.setUpdatedBy(peerUser.getUpdatedBy());
        return peerUserDto;
    }

    public PeerUser dtoToDomain(PeerUserDto peerUserDto) {
        PeerUser peerUser = new PeerUser();
        peerUser.setUserId(peerUserDto.getUserId());
        peerUser.setAge(peerUserDto.getAge());
        peerUser.setGender(peerUserDto.getGender());
        peerUser.setTier(peerUserDto.getTier());
        peerUser.setAverageMonthlyIncome(peerUserDto.getAverageMonthlyIncome());
        peerUser.setNoOfTransactionsPerMonth(peerUserDto.getNoOfTransactionsPerMonth());
        peerUser.setPeerGroupId(peerUserDto.getPeerGroupId());
        peerUser.setAveragePeerGoalSavingAmount(peerUserDto.getAveragePeerGoalSavingAmount());
        peerUser.setFegoScorePercentile(peerUserDto.getFegoScorePercentile());
        peerUser.setAccountAverageBalance(peerUserDto.getAccountAverageBalance());
        peerUser.setAverageFixedSalaryIncome(peerUserDto.getAverageFixedSalaryIncome());
        peerUser.setAverageFixedNonSalaryIncome(peerUserDto.getAverageFixedNonSalaryIncome());
        peerUser.setAverageVaryingIncome(peerUserDto.getAverageVaryingIncome());
        peerUser.setAverageFixedExpenses(peerUserDto.getAverageFixedExpenses());
        peerUser.setAverageVariableExpenses(peerUserDto.getAverageVariableExpenses());
        peerUser.setPrimaryAccountId(peerUserDto.getPrimaryAccountId());
        peerUser.setGoalSaveAmount(peerUserDto.getGoalSaveAmount());
        peerUser.setFreeCashAmount(peerUserDto.getFreeCashAmount());
        peerUser.setAutoDeductFrequency(peerUserDto.getAutoDeductFrequency());
        peerUser.setInstantSaveFrequency(peerUserDto.getInstantSaveFrequency());
        peerUser.setRoundUpFrequency(peerUserDto.getRoundUpFrequency());
        if (CollectionUtils.isEmpty(peerUserDto.getCategories())) {
            peerUser.setCategories(Constants.EMPTY);
        } else {
            peerUser.setCategories(StringUtils.join(peerUserDto.getCategories(), Constants.COMMA));
        }
        peerUser.setDeleted(peerUserDto.getIsDeleted());
        peerUser.setCreatedBy(peerUserDto.getCreatedBy());
        peerUser.setUpdatedBy(peerUserDto.getUpdatedBy());
        return peerUser;
    }
}