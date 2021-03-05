package com.fego.transaction.controller;

import com.fego.transaction.common.response.SuccessResponse;
import com.fego.transaction.dto.DailyTransactionResponseDto;
import com.fego.transaction.dto.MonthlyOverviewDto;
import com.fego.transaction.dto.SuccessResponseDto;
import com.fego.transaction.dto.TransactionCategoryUpdateDto;
import com.fego.transaction.dto.integration.ConsentApproveReqDto;
import com.fego.transaction.service.FinancialCalendarService;
import com.fego.transaction.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Api(tags = "Transaction Controller")
@RestController
@RequestMapping("v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final FinancialCalendarService financialCalendarService;

    public TransactionController(TransactionService transactionService, FinancialCalendarService financialCalendarService) {
        this.transactionService = transactionService;
        this.financialCalendarService = financialCalendarService;
    }

    @Transactional
    @PostMapping
    @ApiOperation(value = "Inserts a list of transactions of an user")
    public SuccessResponse<SuccessResponseDto> saveTransaction(
            @RequestBody @Valid ConsentApproveReqDto consentApproveReqDto) {
        return new SuccessResponse<>(transactionService.addTxnData(consentApproveReqDto), HttpStatus.OK);
    }

    @Transactional
    @GetMapping
    @ApiOperation(value = "Returns the Transactions made by an User on a particular date")
    public SuccessResponse<DailyTransactionResponseDto> getTransaction(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new SuccessResponse<>(transactionService.getTransactionOnDate(date), HttpStatus.OK);
    }

    public void arun(){

    }

    public void balaji(){
        
    }

    @Transactional
    @GetMapping("/monthly-overview")
    @ApiOperation(value = "Expense, Income and Savings made by an User in a respective month")
    public SuccessResponse<MonthlyOverviewDto> monthlyOverview(@RequestParam(name = "year") int year, @RequestParam(name = "month") int month) {
        return new SuccessResponse<>(financialCalendarService.getMonthlyOverview(year, month), HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/{id}")
    @ApiOperation(value = "Updates the Category of a Transaction")
    public SuccessResponse<SuccessResponseDto> updateCategory(@PathVariable(name = "id") long id, @RequestBody @Valid TransactionCategoryUpdateDto transactionCategoryUpdateDto) {
        return new SuccessResponse<>(transactionService.updateCategory(id, transactionCategoryUpdateDto), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/categories")
    @ApiOperation(value = "Returns a list of category based on the active Categorization Partner (Like 3loq, finbit)")
    public SuccessResponse<List<String>> transactionCategories() {
        return new SuccessResponse<>(transactionService.getFegoCategories(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/sync")
    @ApiOperation(value = "Retrieves transactions from OneMoney on a frequency basis")
    public SuccessResponse<SuccessResponseDto> transactionSync() {
        return new SuccessResponse<>(transactionService.transactionSync(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/calculate/peer-user")
    @ApiOperation(value = "Calculates Free Cash for all users")
    public SuccessResponse<SuccessResponseDto> peerUserCalculation() {
        return new SuccessResponse<>(transactionService.peerUserCalculationForAllUser(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/calculate/financial-calendar")
    @ApiOperation(value = "Calculates Financial Calendar data for all users")
    public SuccessResponse<SuccessResponseDto> financialCalendarCalculation() {
        return new SuccessResponse<>(transactionService.calculateFinancialCalendarForAllUser(), HttpStatus.OK);
    }
}