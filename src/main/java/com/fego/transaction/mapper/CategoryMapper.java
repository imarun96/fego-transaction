package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.dto.CategoryDto;
import com.fego.transaction.entity.Category;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class CategoryMapper implements BaseMapper<Category, CategoryDto> {

    public CategoryDto domainToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setPartnerName(category.getPartnerName());
        categoryDto.setFegoCategory(category.getFegoCategory());
        categoryDto.setFegoSubCategory(category.getFegoSubCategory());
        categoryDto.setFirstLevelCategory(category.getFirstLevelCategory());
        categoryDto.setSecondLevelCategory(category.getSecondLevelCategory());
        categoryDto.setThirdLevelCategory(category.getThirdLevelCategory());
        categoryDto.setFegoTransactionType(category.getFegoTransactionType());
        categoryDto.setFixedTransaction(category.getFixedTransaction());
        categoryDto.setPartOfBudget(category.getPartOfBudget());
        categoryDto.setPartOfReport(category.getPartOfReport());
        categoryDto.setId(category.getId());
        categoryDto.setIsDeleted(category.isDeleted());
        categoryDto.setCreatedAt(category.getCreatedAt());
        categoryDto.setUpdatedAt(category.getUpdatedAt());
        categoryDto.setCreatedBy(category.getCreatedBy());
        categoryDto.setUpdatedBy(category.getUpdatedBy());
        return categoryDto;
    }

    public Category dtoToDomain(CategoryDto categoryDto) {
        Category category = new Category();
        category.setPartnerName(categoryDto.getPartnerName());
        category.setFegoCategory(categoryDto.getFegoCategory());
        category.setFegoSubCategory(categoryDto.getFegoSubCategory());
        category.setFirstLevelCategory(categoryDto.getFirstLevelCategory());
        category.setSecondLevelCategory(categoryDto.getSecondLevelCategory());
        category.setThirdLevelCategory(categoryDto.getThirdLevelCategory());
        category.setFegoTransactionType(categoryDto.getFegoTransactionType());
        category.setFixedTransaction(categoryDto.getFixedTransaction());
        category.setPartOfBudget(categoryDto.getPartOfBudget());
        category.setPartOfReport(categoryDto.getPartOfReport());
        category.setDeleted(categoryDto.getIsDeleted());
        category.setCreatedBy(categoryDto.getCreatedBy());
        category.setUpdatedBy(categoryDto.getUpdatedBy());
        return category;
    }
}