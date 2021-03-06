package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class UnprocessableEntity extends ServicesException {
    public UnprocessableEntity(final ErrorCode code, final String message) {
        super(code, message);
    }
}