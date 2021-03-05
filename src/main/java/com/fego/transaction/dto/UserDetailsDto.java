package com.fego.transaction.dto;

/**
 * @author Arun Balaji Rajasekaran
 */

public class UserDetailsDto {
    private long id;
    private String gender;
    private Integer age;
    private String tier;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }
}