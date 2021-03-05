package com.fego.transaction.dto;

import com.fego.transaction.dto.integration.FipResponseDto;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class UserRegistrationResponseDto {
    private List<AccountDto> accountDto;
    private FipResponseDto fipResponseDto;

    public List<AccountDto> getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(List<AccountDto> accountDto) {
        this.accountDto = accountDto;
    }

    public FipResponseDto getFipResponseDto() {
        return fipResponseDto;
    }

    public void setFipResponseDto(FipResponseDto fipResponseDto) {
        this.fipResponseDto = fipResponseDto;
    }
}