package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.config.PropertyConfig;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.GoalCategory;
import com.fego.transaction.dto.GoalDto;
import com.fego.transaction.dto.RuleDto;
import com.fego.transaction.entity.Goal;
import com.fego.transaction.entity.Rule;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class GoalMapper implements BaseMapper<Goal, GoalDto> {

    private final PropertyConfig propertyConfig;
    private final RuleMapper ruleMapper;

    public GoalMapper(PropertyConfig propertyConfig, RuleMapper ruleMapper) {
        this.propertyConfig = propertyConfig;
        this.ruleMapper = ruleMapper;
    }

    public GoalDto domainToDto(Goal goal) {
        GoalDto goalDto = new GoalDto();
        goalDto.setUserId(goal.getUserId());
        goalDto.setName(goal.getName());
        goalDto.setTargetDate(goal.getTargetDate());
        goalDto.setLastPopUpDate(goal.getLastPopUpDate());
        goalDto.setTargetAmount(goal.getTargetAmount());
        if (goal.getGoalCategory() == GoalCategory.ASSET.code() || goal.getGoalCategory() == GoalCategory.PLACE.code()) {
            goalDto.setImage(propertyConfig.getAws().get(Constants.BUCKET_BASE_URL) + goal.getImage());
        } else {
            goalDto.setImage(null);
        }
        goalDto.setGoalCategory(goal.getGoalCategory());
        goalDto.setCompleted(goal.isCompleted());
        if (!CollectionUtils.isEmpty(goal.getRules())) {
            goal.getRules().forEach(goalRules -> {
                if (CollectionUtils.isEmpty(goalDto.getRules())) {
                    List<RuleDto> dto = new ArrayList<>();
                    dto.add(ruleMapper.domainToDto(goalRules));
                    goalDto.setRules(dto);
                } else {
                    goalDto.getRules().add(ruleMapper.domainToDto(goalRules));
                }
            });
        }
        goalDto.setId(goal.getId());
        goalDto.setIsDeleted(goal.isDeleted());
        goalDto.setCreatedAt(goal.getCreatedAt());
        goalDto.setUpdatedAt(goal.getUpdatedAt());
        goalDto.setCreatedBy(goal.getCreatedBy());
        goalDto.setUpdatedBy(goal.getUpdatedBy());
        return goalDto;
    }

    public Goal dtoToDomain(GoalDto goalDto) {
        Goal goal = new Goal();
        goal.setUserId(goalDto.getUserId());
        goal.setName(goalDto.getName());
        goal.setLastPopUpDate(goalDto.getLastPopUpDate());
        goal.setTargetDate(goalDto.getTargetDate());
        goal.setTargetAmount(goalDto.getTargetAmount());
        goal.setImage(goalDto.getImage());
        goal.setGoalCategory(goalDto.getGoalCategory());
        goal.setCompleted(goalDto.isCompleted());
        if (!CollectionUtils.isEmpty(goalDto.getRules())) {
            goalDto.getRules().forEach(ruleDto -> {
                if (CollectionUtils.isEmpty(goal.getRules())) {
                    List<Rule> rules = new ArrayList<>();
                    rules.add(ruleMapper.dtoToDomain(ruleDto));
                    goal.setRules(rules);
                } else {
                    goal.getRules().add(ruleMapper.dtoToDomain(ruleDto));
                }
            });
        }
        goal.setDeleted(goalDto.getIsDeleted());
        goal.setCreatedBy(goalDto.getCreatedBy());
        goal.setUpdatedBy(goalDto.getUpdatedBy());
        return goal;
    }
}