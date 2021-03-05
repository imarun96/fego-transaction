package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.utils.CacheDataUtil;
import com.fego.transaction.dto.PeerUserDto;
import com.fego.transaction.entity.PeerUser;
import com.fego.transaction.repository.PeerUserRepository;
import com.fego.transaction.task.PeerUserTask;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class PeerUserService extends BaseService<PeerUser, PeerUserDto, PeerUserTask> {

    public PeerUserService(PeerUserRepository peerUserRepository, BaseMapper<PeerUser, PeerUserDto> freeCashFlowMapper,
                           IdSpecifications<PeerUser> peerUserIdSpecifications, BaseTask<PeerUser> freeCashFlowTask) {
        super(peerUserRepository, freeCashFlowMapper, peerUserIdSpecifications, freeCashFlowTask);
    }

    /**
     * Outputs the Free Cash amount of the current logged in user.
     *
     * @return BigDecimal - Free cash amount.
     */
    public BigDecimal getFreeCashAmount() {
        return this.findByUserId(CacheDataUtil.getUserId()).getFreeCashAmount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAdd(PeerUserDto peerUserDto) {
        peerUserDto.setCreatedBy(0L);
        peerUserDto.setUpdatedBy(0L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validatePatch(PeerUserDto peerUserDto) {
        peerUserDto.setUpdatedBy(0L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doPatch(PeerUser incomingPeerUser, PeerUser toUpdatePeerUser) {
        if (Objects.nonNull(incomingPeerUser.getGoalSaveAmount())) {
            toUpdatePeerUser.setGoalSaveAmount(incomingPeerUser.getGoalSaveAmount());
        }
        if (Objects.nonNull(incomingPeerUser.getFreeCashAmount())) {
            toUpdatePeerUser.setFreeCashAmount(incomingPeerUser.getFreeCashAmount());
        }
        if (Objects.nonNull(incomingPeerUser.getAutoDeductFrequency())) {
            toUpdatePeerUser.setAutoDeductFrequency(incomingPeerUser.getAutoDeductFrequency());
        }
        if (Objects.nonNull(incomingPeerUser.getInstantSaveFrequency())) {
            toUpdatePeerUser.setInstantSaveFrequency(incomingPeerUser.getInstantSaveFrequency());
        }
        if (Objects.nonNull(incomingPeerUser.getRoundUpFrequency())) {
            toUpdatePeerUser.setRoundUpFrequency(incomingPeerUser.getRoundUpFrequency());
        }
        if (Objects.nonNull(incomingPeerUser.getCategories())) {
            toUpdatePeerUser.setCategories(incomingPeerUser.getCategories());
        }
        continuePatch(incomingPeerUser, toUpdatePeerUser);
    }

    /**
     * {@inheritDoc}
     */
    private void continuePatch(PeerUser incomingPeerUser, PeerUser toUpdatePeerUser) {
        if (Objects.nonNull(incomingPeerUser.getAverageMonthlyIncome())) {
            toUpdatePeerUser.setAverageMonthlyIncome(incomingPeerUser.getAverageMonthlyIncome());
        }
        if (Objects.nonNull(incomingPeerUser.getAccountAverageBalance())) {
            toUpdatePeerUser.setAccountAverageBalance(incomingPeerUser.getAccountAverageBalance());
        }
        if (Objects.nonNull(incomingPeerUser.getAverageFixedSalaryIncome())) {
            toUpdatePeerUser.setAverageFixedSalaryIncome(incomingPeerUser.getAverageFixedSalaryIncome());
        }
        if (Objects.nonNull(incomingPeerUser.getAverageFixedNonSalaryIncome())) {
            toUpdatePeerUser.setAverageFixedNonSalaryIncome(incomingPeerUser.getAverageFixedNonSalaryIncome());
        }
        if (Objects.nonNull(incomingPeerUser.getAverageVaryingIncome())) {
            toUpdatePeerUser.setAverageVaryingIncome(incomingPeerUser.getAverageVaryingIncome());
        }
        if (Objects.nonNull(incomingPeerUser.getAverageFixedExpenses())) {
            toUpdatePeerUser.setAverageFixedExpenses(incomingPeerUser.getAverageFixedExpenses());
        }
        if (Objects.nonNull(incomingPeerUser.getAverageVariableExpenses())) {
            toUpdatePeerUser.setAverageVariableExpenses(incomingPeerUser.getAverageVariableExpenses());
        }
    }
}