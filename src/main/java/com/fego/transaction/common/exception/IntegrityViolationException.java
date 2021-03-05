package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class IntegrityViolationException extends ServicesException {

    public IntegrityViolationException(final ErrorCode code, final String message) {
        super(code, message);
    }
}