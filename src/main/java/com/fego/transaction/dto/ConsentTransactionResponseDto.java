package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fego.transaction.dto.integration.AccountTransactionDto;
import com.fego.transaction.dto.integration.ConsentArtefactDataDto;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class ConsentTransactionResponseDto {

    @JsonProperty("consentApproveStatus")
    private SuccessResponseDto successResponseDto;

    @JsonProperty("consentArtefactData")
    private ConsentArtefactDataDto consentArtefactData;

    @JsonProperty("accountsTransactionData")
    private List<AccountTransactionDto> transactionData;

    public SuccessResponseDto getConsentResponseDto() {
        return successResponseDto;
    }

    public void setConsentResponseDto(SuccessResponseDto successResponseDto) {
        this.successResponseDto = successResponseDto;
    }

    public ConsentArtefactDataDto getConsentArtefactData() {
        return consentArtefactData;
    }

    public void setConsentArtefactData(ConsentArtefactDataDto consentArtefactData) {
        this.consentArtefactData = consentArtefactData;
    }

    public List<AccountTransactionDto> getTransactionData() {
        return transactionData;
    }

    public void setTransactionData(List<AccountTransactionDto> transactionData) {
        this.transactionData = transactionData;
    }
}