package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class UserExistException extends ServicesException {

    public UserExistException(final ErrorCode code, final String message) {
        super(code, message);
    }
}