package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Arun Balaji Rajasekaran
 */

public class HoldingDto {
    private String amc;
    private String registrar;
    private String schemeCode;
    private String isin;
    private String ucc;
    private String amfiCode;
    private String folioNo;
    private String dividendType;
    private String mode;
    @JsonProperty("FatcaStatus")
    private String fatcaStatus;
    private String units;
    private String closingUnits;
    private String lienUnits;
    private String rate;
    private String nav;
    private String lockingUnits;

    public String getAmc() {
        return amc;
    }

    public void setAmc(String amc) {
        this.amc = amc;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getUcc() {
        return ucc;
    }

    public void setUcc(String ucc) {
        this.ucc = ucc;
    }

    public String getAmfiCode() {
        return amfiCode;
    }

    public void setAmfiCode(String amfiCode) {
        this.amfiCode = amfiCode;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getDividendType() {
        return dividendType;
    }

    public void setDividendType(String dividendType) {
        this.dividendType = dividendType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getFatcaStatus() {
        return fatcaStatus;
    }

    public void setFatcaStatus(String fatcaStatus) {
        this.fatcaStatus = fatcaStatus;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getClosingUnits() {
        return closingUnits;
    }

    public void setClosingUnits(String closingUnits) {
        this.closingUnits = closingUnits;
    }

    public String getLienUnits() {
        return lienUnits;
    }

    public void setLienUnits(String lienUnits) {
        this.lienUnits = lienUnits;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public String getLockingUnits() {
        return lockingUnits;
    }

    public void setLockingUnits(String lockingUnits) {
        this.lockingUnits = lockingUnits;
    }
}