package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

public class AccountDto {

    @JsonProperty("type")
    private String type;

    @JsonProperty("data")
    private AccountDataDto accountDataDto;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AccountDataDto getData() {
        return accountDataDto;
    }

    public void setData(AccountDataDto accountDataDto) {
        this.accountDataDto = accountDataDto;
    }
}