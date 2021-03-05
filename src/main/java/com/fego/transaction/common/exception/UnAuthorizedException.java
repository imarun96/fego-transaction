package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class UnAuthorizedException extends ServicesException {

    public UnAuthorizedException(final ErrorCode code, final String message) {
        super(code, message);
    }
}