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
public class SavingsMeta extends BaseModel {

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