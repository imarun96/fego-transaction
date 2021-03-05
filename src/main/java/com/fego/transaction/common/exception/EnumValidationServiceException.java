package com.fego.transaction.common.exception;

/**
 * <p>
 * Added in the enum exception class.
 * </p>
 *
 * @author Rajasekar Nagarajan created on May 7, 2020
 */
public class EnumValidationServiceException extends Exception {
    private String enumValue = null;
    private String enumName = null;

    public EnumValidationServiceException(String enumValue, String enumName) {
        super(enumValue);
        this.enumValue = enumValue;
        this.enumName = enumName;
    }

    public EnumValidationServiceException(String enumValue, String enumName, Throwable cause) {
        super(enumValue, cause);
        this.enumValue = enumValue;
        this.enumName = enumName;
    }

    public String getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(String enumValue) {
        this.enumValue = enumValue;
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }
}