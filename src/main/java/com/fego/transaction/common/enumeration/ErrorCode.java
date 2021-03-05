package com.fego.transaction.common.enumeration;

public enum ErrorCode {

    GENERAL_ERROR(5000), ARGUMENT_NOT_VALID_EXCEPTION(5001), INVALID_FILE(5002), BAD_REQUEST(5003),
    RECORD_NOT_FOUND(5004), UNAUTHORIZED_ONLY_OWNER(5005), UNAUTHORIZED_USER(5006), INSUFFICIENT_PRIVILEGES(5007),
    USER_NOT_FOUND(5008), VALIDATE_FIELD_NOT_NULL(5009), INVALID_URL_TITLE(5010), INVALID_URL_DESCRIPTION(5011),
    INVALID_URL(5012);

    private final int key;

    ErrorCode(int key) {
        this.key = key;
    }

    public int key() {
        return this.key;
    }
}