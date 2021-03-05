package com.fego.transaction.dto;

import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

public class GoalCategoriesResponseDto {
    private Boolean justStartSavingFlag;
    private List<SavingsMetaDto> savingsMetaDto;

    public Boolean getJustStartSavingFlag() {
        return justStartSavingFlag;
    }

    public void setJustStartSavingFlag(Boolean justStartSavingFlag) {
        this.justStartSavingFlag = justStartSavingFlag;
    }

    public List<SavingsMetaDto> getSavingsMetaDto() {
        return savingsMetaDto;
    }

    public void setSavingsMetaDto(List<SavingsMetaDto> savingsMetaDto) {
        this.savingsMetaDto = savingsMetaDto;
    }
}