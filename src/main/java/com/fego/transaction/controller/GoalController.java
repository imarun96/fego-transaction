package com.fego.transaction.controller;

import com.fego.transaction.common.response.SuccessResponse;
import com.fego.transaction.dto.*;
import com.fego.transaction.service.GoalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Api(tags = "Goal Controller")
@RestController
@RequestMapping("v1/goal")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @Transactional
    @PostMapping
    @ApiOperation(value = "Inserts a new Goal")
    public SuccessResponse<GoalLoadResponseDto> add(@RequestBody @Valid GoalRequestDto goalRequestDto) {
        return new SuccessResponse<>(goalService.add(goalRequestDto), HttpStatus.OK);
    }

    @Transactional
    @GetMapping
    @ApiOperation(value = "Returns the list of Goals created by an User")
    public SuccessResponse<GoalsDto> goals() {
        return new SuccessResponse<>(goalService.goals(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/overview/{goalId}")
    @ApiOperation(value = "Returns a complete overview of a Goal created by an User")
    public SuccessResponse<GoalDataDto> overview(@PathVariable("goalId") long goalId) {
        return new SuccessResponse<>(goalService.goalOverview(goalId), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{goalId}")
    @ApiOperation(value = "Returns the details of a particular goal created by an user")
    public SuccessResponse<GoalLoadResponseDto> get(@PathVariable("goalId") long goalId) {
        return new SuccessResponse<>(goalService.getGoal(goalId), HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{goalId}")
    @ApiOperation(value = "Updates the details of a Goal")
    public SuccessResponse<GoalLoadResponseDto> update(@PathVariable("goalId") long goalId, @RequestBody @Valid GoalRequestDto goalRequestDto) {
        return new SuccessResponse<>(goalService.updateGoal(goalId, goalRequestDto), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{goalId}")
    @ApiOperation(value = "Deletes a particular Goal")
    public SuccessResponse<SuccessResponseDto> delete(@PathVariable("goalId") long goalId) {
        return new SuccessResponse<>(goalService.deleteGoal(goalId), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/categories")
    @ApiOperation(value = "Outputs the different categories of a Goal")
    public SuccessResponse<GoalCategoriesResponseDto> categories() {
        return new SuccessResponse<>(goalService.getGoalCategories(), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/estimate-date")
    @ApiOperation(value = "Returns an Estimated date when the user cannot achieve a Target amount within a particular date")
    public SuccessResponse<EstimatedDateResponseDto> estimateDate(@RequestBody @Valid EstimatedDateRequestDto estimatedDateRequestDto) {
        return new SuccessResponse<>(goalService.getEstimatedDate(estimatedDateRequestDto), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/rules/{savingPreference}")
    @ApiOperation(value = "Returns the Goal rules")
    public SuccessResponse<RulesResponseDto> rules(@PathVariable("savingPreference") long savingPreference) {
        return new SuccessResponse<>(goalService.getRules(savingPreference), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/instant-popup")
    @ApiOperation(value = "Returns options to display for Instant Saving Pop Up")
    public SuccessResponse<List<BigDecimal>> showPop() {
        return new SuccessResponse<>(goalService.getAmountForInstantSave(), HttpStatus.OK);
    }
}