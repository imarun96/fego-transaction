package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.dto.RuleDto;
import com.fego.transaction.entity.Rule;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class RuleMapper implements BaseMapper<Rule, RuleDto> {

    public RuleDto domainToDto(Rule rule) {
        RuleDto ruleDto = new RuleDto();
        ruleDto.setGoalId(rule.getGoalId());
        ruleDto.setRuleId(rule.getRuleId());
        ruleDto.setAutoDeductAmount(rule.getAutoDeductAmount());
        ruleDto.setId(rule.getId());
        ruleDto.setIsDeleted(rule.isDeleted());
        ruleDto.setCreatedAt(rule.getCreatedAt());
        ruleDto.setUpdatedAt(rule.getUpdatedAt());
        ruleDto.setCreatedBy(rule.getCreatedBy());
        ruleDto.setUpdatedBy(rule.getUpdatedBy());
        return ruleDto;
    }

    public Rule dtoToDomain(RuleDto ruleDto) {
        Rule rule = new Rule();
        rule.setGoalId(ruleDto.getGoalId());
        rule.setRuleId(ruleDto.getRuleId());
        rule.setAutoDeductAmount(ruleDto.getAutoDeductAmount());
        rule.setDeleted(ruleDto.getIsDeleted());
        rule.setCreatedBy(ruleDto.getCreatedBy());
        rule.setUpdatedBy(ruleDto.getUpdatedBy());
        return rule;
    }
}