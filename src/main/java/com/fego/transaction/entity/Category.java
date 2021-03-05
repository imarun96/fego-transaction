package com.fego.transaction.entity;

import com.fego.transaction.common.base.BaseModel;
import com.fego.transaction.common.constants.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Arun Balaji Rajasekaran
 */

@Entity
@Table(schema = Constants.SCHEMA)
public class Category extends BaseModel {

    private String partnerName;
    private String fegoCategory;
    private String fegoSubCategory;
    private String firstLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
    private String fegoTransactionType;
    private Boolean isFixedTransaction;
    private Boolean isPartOfBudget;
    private Boolean isPartOfReport;

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getFegoCategory() {
        return fegoCategory;
    }

    public void setFegoCategory(String fegoCategory) {
        this.fegoCategory = fegoCategory;
    }

    public String getFegoSubCategory() {
        return fegoSubCategory;
    }

    public void setFegoSubCategory(String fegoSubCategory) {
        this.fegoSubCategory = fegoSubCategory;
    }

    public String getFirstLevelCategory() {
        return firstLevelCategory;
    }

    public void setFirstLevelCategory(String firstLevelCategory) {
        this.firstLevelCategory = firstLevelCategory;
    }

    public String getSecondLevelCategory() {
        return secondLevelCategory;
    }

    public void setSecondLevelCategory(String secondLevelCategory) {
        this.secondLevelCategory = secondLevelCategory;
    }

    public String getThirdLevelCategory() {
        return thirdLevelCategory;
    }

    public void setThirdLevelCategory(String thirdLevelCategory) {
        this.thirdLevelCategory = thirdLevelCategory;
    }

    public String getFegoTransactionType() {
        return fegoTransactionType;
    }

    public void setFegoTransactionType(String fegoTransactionType) {
        this.fegoTransactionType = fegoTransactionType;
    }

    public Boolean getFixedTransaction() {
        return isFixedTransaction;
    }

    public void setFixedTransaction(Boolean fixedTransaction) {
        isFixedTransaction = fixedTransaction;
    }

    public Boolean getPartOfBudget() {
        return isPartOfBudget;
    }

    public void setPartOfBudget(Boolean partOfBudget) {
        isPartOfBudget = partOfBudget;
    }

    public Boolean getPartOfReport() {
        return isPartOfReport;
    }

    public void setPartOfReport(Boolean partOfReport) {
        isPartOfReport = partOfReport;
    }
}