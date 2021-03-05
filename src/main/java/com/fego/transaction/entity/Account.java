package com.fego.transaction.entity;

import com.fego.transaction.common.base.BaseModel;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.dto.UserInfoDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

@Entity
@Table(schema = Constants.SCHEMA)
public class Account extends BaseModel {
    @Column(nullable = false)
    private long userId;
    private String accountType;
    private String productNumber;
    private String maskedProductNumber;
    private String partnerId;
    private String partnerName;
    private String partnerType;
    private String fullName;
    private long consentId;
    private String productType;
    private String type;
    private String nominee;
    private String bankName;
    private LocalDate openingDate;
    @Column(scale = 5, precision = 22)
    private BigDecimal currentOdLimit;
    @Column(scale = 5, precision = 22)
    private BigDecimal currentBalance;
    @Column(scale = 5, precision = 22)
    private BigDecimal availableCreditLimit;
    @Column(scale = 5, precision = 22)
    private BigDecimal creditLimit;
    @Column(scale = 5, precision = 22)
    private BigDecimal drawingLimit;
    private String status;
    private String ifscCode;
    private String branch;
    private String micrCode;
    private String currency;
    private String facility;
    private String longitude;
    private String latitude;
    private String fipId;
    private String linkAccountReferenceNumber;
    private Boolean isAccountLinked;
    @Transient
    private UserInfoDto userInfo;
    private LocalDate dueDate;
    @Column(scale = 5, precision = 22)
    private BigDecimal currentDue;
    private String image;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
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

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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