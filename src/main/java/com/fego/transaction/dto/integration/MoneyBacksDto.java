package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MoneyBacksDto {

    @JsonProperty("MoneyBack")
    private MoneyBackDto moneyBack;

    public MoneyBackDto getMoneyBack() {
        return moneyBack;
    }

    public void setMoneyBack(MoneyBackDto moneyBack) {
        this.moneyBack = moneyBack;
    }
}