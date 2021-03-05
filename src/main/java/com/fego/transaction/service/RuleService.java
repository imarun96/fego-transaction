package com.fego.transaction.service;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.base.BaseService;
import com.fego.transaction.common.base.BaseTask;
import com.fego.transaction.common.base.specification.IdSpecifications;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.utils.CacheDataUtil;
import com.fego.transaction.dto.PeerUserDto;
import com.fego.transaction.dto.RuleDto;
import com.fego.transaction.entity.Rule;
import com.fego.transaction.repository.RuleRepository;
import com.fego.transaction.task.RuleTask;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Arun Balaji Rajasekaran
 */

@Service
public class RuleService extends BaseService<Rule, RuleDto, RuleTask> {

    private final IdSpecifications<Rule> specifications;
    private final RuleRepository repository;
    private final PeerUserService peerUserService;

    public RuleService(RuleRepository ruleRepository,
                       BaseMapper<Rule, RuleDto> savingRuleMapper, IdSpecifications<Rule> specifications,
                       BaseTask<Rule> ruleTask, RuleRepository repository, PeerUserService peerUserService) {
        super(ruleRepository, savingRuleMapper, specifications, ruleTask);
        this.specifications = specifications;
        this.repository = repository;
        this.peerUserService = peerUserService;
    }

    /**
     * Outputs Auto Deduct amount of a particular Goal.
     *
     * @param goalId - The id of particular Goal for which Auto Deduct amount has to be calculated.
     * @return BigDecimal - Actual Auto Deduct is returned if present or else ZERO.
     */
    public BigDecimal getAutoDeductAmount(Long goalId) {
        Specification<Rule> baseSpecification = specifications.findByAutoDeduct(goalId);
        PeerUserDto peerUserDto = peerUserService.findByUserId(CacheDataUtil.getUserId());
        int frequency = peerUserDto.getAutoDeductFrequency();
        Optional<Rule> optionalDto = this.findOneModel(baseSpecification);
        if (optionalDto.isEmpty()) {
            return BigDecimal.ZERO;
        }
        if (frequency >= 1 && frequency <= 28) {
            return optionalDto.get().getAutoDeductAmount();
        }
        if (frequency >= 29 && frequency <= 35) {
            return optionalDto.get().getAutoDeductAmount().multiply(Constants.SEVEN);
        }
        if (frequency == 36) {
            return optionalDto.get().getAutoDeductAmount().multiply(Constants.THIRTY);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Delete rules associated with a Goal.
     *
     * @param ruleIds - List of Rule Id which are associated with Goal.
     */
    public void deletePostTopicByIds(List<Long> ruleIds) {
        if (!CollectionUtils.isEmpty(ruleIds)) {
            repository.deleteRuleByIds(ruleIds);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preDeleteByGid(Rule toDeleteRule) {
        toDeleteRule.setUpdatedBy(CacheDataUtil.getUserId());
    }
}