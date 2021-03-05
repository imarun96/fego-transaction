package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class BadRequestException extends ServicesException {

    public BadRequestException(ErrorCode code, String message) {
        super(code, message);
    }
}