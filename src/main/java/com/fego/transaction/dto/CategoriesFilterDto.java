package com.fego.transaction.dto;

/**
 * @author Arun Balaji Rajasekaran
 */

public class CategoriesFilterDto {
    private String fegoCategory;
    private String fegoSubCategory;

    public CategoriesFilterDto(String fegoCategory, String fegoSubCategory) {
        this.fegoCategory = fegoCategory;
        this.fegoSubCategory = fegoSubCategory;
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
}