package com.fego.transaction.dto;

import com.fego.transaction.common.base.BaseDto;

/**
 * @author Arun Balaji Rajasekaran
 */

public class SavingsMetaDto extends BaseDto {

    private String category;
    private String name;
    private String fullSizeImage;
    private String thumbnailImage;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullSizeImage() {
        return fullSizeImage;
    }

    public void setFullSizeImage(String fullSizeImage) {
        this.fullSizeImage = fullSizeImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }
}