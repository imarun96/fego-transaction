package com.fego.transaction.entity;

import com.fego.transaction.common.base.BaseModel;
import com.fego.transaction.common.constants.Constants;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arun Balaji Rajasekaran
 */

@Entity
@Table(schema = Constants.SCHEMA)
public class Goal extends BaseModel {

    private long userId;
    private String name;
    private LocalDate targetDate;
    @Column(scale = 5, precision = 22)
    private BigDecimal targetAmount;
    private String image;
    private Integer goalCategory;
    private boolean isCompleted;
    private LocalDate lastPopUpDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_id")
    private List<Rule> rules = new ArrayList<>();

    public LocalDate getLastPopUpDate() {
        return lastPopUpDate;
    }

    public void setLastPopUpDate(LocalDate lastPopUpDate) {
        this.lastPopUpDate = lastPopUpDate;
    }

    public Integer getGoalCategory() {
        return goalCategory;
    }

    public void setGoalCategory(Integer goalCategory) {
        this.goalCategory = goalCategory;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}