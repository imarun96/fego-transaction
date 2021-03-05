package com.fego.transaction.dto.integration;

/**
 * @author Arun Balaji Rajasekaran
 */

public class ConsentApproveReqDto {
    private String otp;
    private String consentHandle;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getConsentHandle() {
        return consentHandle;
    }

    public void setConsentHandle(String consentHandle) {
        this.consentHandle = consentHandle;
    }
}