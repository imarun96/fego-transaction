package com.fego.transaction.controller;

import com.fego.transaction.common.response.SuccessResponse;
import com.fego.transaction.dto.AccountBalanceResponseDto;
import com.fego.transaction.dto.AccountsResponseDto;
import com.fego.transaction.dto.SuccessResponseDto;
import com.fego.transaction.dto.UserRegistrationResponseDto;
import com.fego.transaction.dto.integration.AccountLinkRequestDto;
import com.fego.transaction.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Api(tags = "Account Controller")
@RestController
@RequestMapping("v1/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void Balaji(){
        
    }

    @Transactional
    @PostMapping
    @ApiOperation(value = "Inserts the Accounts of an User")
    public SuccessResponse<SuccessResponseDto> add(@RequestBody @Valid UserRegistrationResponseDto userRegistrationResponseDto) {
        return new SuccessResponse<>(accountService.saveAccount(userRegistrationResponseDto), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/link")
    @ApiOperation(value = "Links an Account to OneMoney")
    public SuccessResponse<SuccessResponseDto> link(
            @RequestBody @Valid AccountLinkRequestDto accountLinkRequestDto) {
        return new SuccessResponse<>(accountService.linkAccount(accountLinkRequestDto), HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/balance")
    @ApiOperation(value = "Outputs the consolidated balances of all the Accounts of an User")
    public SuccessResponse<AccountBalanceResponseDto> balance() {
        return new SuccessResponse<>(accountService.findAccountBalance(), HttpStatus.OK);
    }

    @Transactional
    @GetMapping
    @ApiOperation(value = "Returns the Accounts of User")
    public SuccessResponse<List<AccountsResponseDto>> getAccounts() {
        return new SuccessResponse<>(accountService.getAccountsOfUser(), HttpStatus.OK);
    }
}
