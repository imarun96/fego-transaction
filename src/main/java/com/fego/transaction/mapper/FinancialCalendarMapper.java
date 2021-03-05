package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.dto.FinancialCalendarDto;
import com.fego.transaction.entity.FinancialCalendar;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class FinancialCalendarMapper implements BaseMapper<FinancialCalendar, FinancialCalendarDto> {

    public FinancialCalendarDto domainToDto(FinancialCalendar financialCalendar) {
        FinancialCalendarDto financialCalendarDto = new FinancialCalendarDto();
        financialCalendarDto.setUserId(financialCalendar.getUserId());
        financialCalendarDto.setStartDateRange(financialCalendar.getStartDateRange());
        financialCalendarDto.setEndDateRange(financialCalendar.getEndDateRange());
        financialCalendarDto.setTransactionType(financialCalendar.getTransactionType());
        financialCalendarDto.setFegoCategory(financialCalendar.getFegoCategory());
        financialCalendarDto.setFegoSubCategory(financialCalendar.getFegoSubCategory());
        financialCalendarDto.setProposedTransactionDate(financialCalendar.getProposedTransactionDate());
        financialCalendarDto.setAmount(financialCalendar.getAmount());
        financialCalendarDto.setActualTransactionDate(financialCalendar.getActualTransactionDate());
        financialCalendarDto.setTransactionFlag(financialCalendar.getTransactionFlag());
        financialCalendarDto.setId(financialCalendar.getId());
        financialCalendarDto.setIsDeleted(financialCalendar.isDeleted());
        financialCalendarDto.setCreatedAt(financialCalendar.getCreatedAt());
        financialCalendarDto.setUpdatedAt(financialCalendar.getUpdatedAt());
        financialCalendarDto.setCreatedBy(financialCalendar.getCreatedBy());
        financialCalendarDto.setUpdatedBy(financialCalendar.getUpdatedBy());
        return financialCalendarDto;
    }

    public FinancialCalendar dtoToDomain(FinancialCalendarDto financialCalendarDto) {
        FinancialCalendar financialCalendar = new FinancialCalendar();
        financialCalendar.setUserId(financialCalendarDto.getUserId());
        financialCalendar.setStartDateRange(financialCalendarDto.getStartDateRange());
        financialCalendar.setEndDateRange(financialCalendarDto.getEndDateRange());
        financialCalendar.setTransactionType(financialCalendarDto.getTransactionType());
        financialCalendar.setFegoCategory(financialCalendarDto.getFegoCategory());
        financialCalendar.setFegoSubCategory(financialCalendarDto.getFegoSubCategory());
        financialCalendar.setProposedTransactionDate(financialCalendarDto.getProposedTransactionDate());
        financialCalendar.setAmount(financialCalendarDto.getAmount());
        financialCalendar.setActualTransactionDate(financialCalendarDto.getActualTransactionDate());
        financialCalendar.setTransactionFlag(financialCalendarDto.getTransactionFlag());
        financialCalendar.setDeleted(financialCalendarDto.getIsDeleted());
        financialCalendar.setCreatedBy(financialCalendarDto.getCreatedBy());
        financialCalendar.setUpdatedBy(financialCalendarDto.getUpdatedBy());
        return financialCalendar;
    }
}