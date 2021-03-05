package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fego.transaction.common.base.BaseDto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

public class AccountDataDto extends BaseDto {
    @JsonIgnore
    private long userId;
    @JsonProperty("accRefNumber")
    private String productNumber;
    @JsonProperty("maskedAccNumber")
    private String maskedProductNumber;
    private String accountType;
    @JsonIgnore
    private String partnerId;
    @JsonIgnore
    private long consentId;
    @JsonIgnore
    private String partnerName;
    @JsonIgnore
    private String partnerType;
    @JsonIgnore
    private String fullName;
    @JsonProperty("accType")
    private String productType;
    @JsonIgnore
    private String type;
    @JsonIgnore
    private String nominee;
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate openingDate;
    @JsonIgnore
    private String bankName;
    @JsonIgnore
    private BigDecimal currentOdLimit;
    @JsonIgnore
    private BigDecimal currentBalance;
    @JsonIgnore
    private BigDecimal availableCreditLimit;
    @JsonIgnore
    private BigDecimal creditLimit;
    @JsonIgnore
    private BigDecimal drawingLimit;
    @JsonIgnore
    private String status;
    @JsonIgnore
    private String ifscCode;
    @JsonIgnore
    private String branch;
    @JsonIgnore
    private String micrCode;
    @JsonIgnore
    private String currency;
    @JsonIgnore
    private String facility;
    @JsonIgnore
    private String longitude;
    @JsonIgnore
    private String latitude;
    @JsonIgnore
    @JsonProperty("fipId")
    private String fipId;
    @JsonIgnore
    private String linkAccountReferenceNumber;
    @JsonIgnore
    private Boolean isAccountLinked;
    @JsonProperty("userInfo")
    private UserInfoDto userInfo;
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    @JsonIgnore
    private BigDecimal currentDue;
    @JsonIgnore
    private String image;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getCurrentDue() {
        return currentDue;
    }

    public void setCurrentDue(BigDecimal currentDue) {
        this.currentDue = currentDue;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getMaskedProductNumber() {
        return maskedProductNumber;
    }

    public void setMaskedProductNumber(String maskedProductNumber) {
        this.maskedProductNumber = maskedProductNumber;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public BigDecimal getCurrentOdLimit() {
        return currentOdLimit;
    }

    public void setCurrentOdLimit(BigDecimal currentOdLimit) {
        this.currentOdLimit = currentOdLimit;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public void setAvailableCreditLimit(BigDecimal availableCreditLimit) {
        this.availableCreditLimit = availableCreditLimit;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getDrawingLimit() {
        return drawingLimit;
    }

    public void setDrawingLimit(BigDecimal drawingLimit) {
        this.drawingLimit = drawingLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getFipId() {
        return fipId;
    }

    public void setFipId(String fipId) {
        this.fipId = fipId;
    }

    public String getLinkAccountReferenceNumber() {
        return linkAccountReferenceNumber;
    }

    public void setLinkAccountReferenceNumber(String linkAccountReferenceNumber) {
        this.linkAccountReferenceNumber = linkAccountReferenceNumber;
    }

    public Boolean getIsAccountLinked() {
        return isAccountLinked;
    }

    public void setIsAccountLinked(Boolean isAccountLinked) {
        this.isAccountLinked = isAccountLinked;
    }

    public UserInfoDto getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoDto userInfo) {
        this.userInfo = userInfo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public long getConsentId() {
        return consentId;
    }

    public void setConsentId(long consentId) {
        this.consentId = consentId;
    }
}