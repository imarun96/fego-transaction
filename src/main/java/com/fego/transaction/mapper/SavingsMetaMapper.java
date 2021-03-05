package com.fego.transaction.mapper;

import com.fego.transaction.common.base.BaseMapper;
import com.fego.transaction.common.config.PropertyConfig;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.dto.SavingsMetaDto;
import com.fego.transaction.entity.SavingsMeta;
import org.springframework.stereotype.Component;

/**
 * @author Arun Balaji Rajasekaran
 */

@Component
public class SavingsMetaMapper implements BaseMapper<SavingsMeta, SavingsMetaDto> {

    private final PropertyConfig propertyConfig;

    public SavingsMetaMapper(PropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }

    public SavingsMetaDto domainToDto(SavingsMeta savingsMeta) {
        SavingsMetaDto savingsMetaDto = new SavingsMetaDto();
        savingsMetaDto.setFullSizeImage(propertyConfig.getAws().get(Constants.BUCKET_BASE_URL) + savingsMeta.getFullSizeImage());
        savingsMetaDto.setThumbnailImage(propertyConfig.getAws().get(Constants.BUCKET_BASE_URL) + savingsMeta.getThumbnailImage());
        savingsMetaDto.setName(savingsMeta.getName());
        savingsMetaDto.setCategory(savingsMeta.getCategory());
        savingsMetaDto.setId(savingsMeta.getId());
        savingsMetaDto.setIsDeleted(savingsMeta.isDeleted());
        savingsMetaDto.setCreatedAt(savingsMeta.getCreatedAt());
        savingsMetaDto.setUpdatedAt(savingsMeta.getUpdatedAt());
        savingsMetaDto.setCreatedBy(savingsMeta.getCreatedBy());
        savingsMetaDto.setUpdatedBy(savingsMeta.getUpdatedBy());
        return savingsMetaDto;
    }

    public SavingsMeta dtoToDomain(SavingsMetaDto savingsMetaDto) {
        SavingsMeta savingsMeta = new SavingsMeta();
        savingsMeta.setFullSizeImage(savingsMetaDto.getFullSizeImage());
        savingsMeta.setThumbnailImage(savingsMetaDto.getThumbnailImage());
        savingsMeta.setName(savingsMetaDto.getName());
        savingsMeta.setCategory(savingsMetaDto.getCategory());
        savingsMeta.setDeleted(savingsMetaDto.getIsDeleted());
        savingsMeta.setCreatedBy(savingsMetaDto.getCreatedBy());
        savingsMeta.setUpdatedBy(savingsMetaDto.getUpdatedBy());
        return savingsMeta;
    }
}