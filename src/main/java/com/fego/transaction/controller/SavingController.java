package com.fego.transaction.controller;

import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.paged.OneBasedIndexedPage;
import com.fego.transaction.common.response.SuccessResponse;
import com.fego.transaction.dto.InstantAmountSaveRequestDto;
import com.fego.transaction.dto.SavingDto;
import com.fego.transaction.dto.SuccessResponseDto;
import com.fego.transaction.service.SavingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @author Arun Balaji Rajasekaran
 */

@Api(tags = "Saving Controller")
@RestController
@RequestMapping("v1/saving")
public class SavingController {
    private final SavingService savingService;

    public SavingController(SavingService savingService) {
        this.savingService = savingService;
    }

    @Transactional
    @PostMapping("/instant-popup/save")
    @ApiOperation(value = "Split and save the Instant Saving Amount among the goals")
    public SuccessResponse<SuccessResponseDto> instantSave(@RequestBody @Valid InstantAmountSaveRequestDto instantAmountSaveRequestDto) {
        return new SuccessResponse<>(savingService.saveInstantAmount(instantAmountSaveRequestDto), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/calculate")
    @ApiOperation(value = "Batch to calculate Saving table data")
    public SuccessResponse<SuccessResponseDto> savingTableCalculation() {
        return new SuccessResponse<>(savingService.batchJobForSavingTable(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/{id}")
    @ApiOperation(value = "Returns Pagination of Savings by a particular goal")
    public SuccessResponse<SavingDto> getSavings(@PathVariable(name = "id") long id, @RequestParam(value = Constants.PAGE_NUMBER) int pageNumber, @RequestParam(value = Constants.PAGE_SIZE) int pageSize, @RequestParam(value = Constants.DIRECTION) String direction, @RequestParam(value = Constants.SORT_BY) String sortBy) {
        Page<SavingDto> savingDtoPage = savingService.getSavings(pageSize, pageNumber, sortBy, direction, id);
        return new SuccessResponse<>(new OneBasedIndexedPage<>(savingDtoPage), HttpStatus.OK);
    }
}