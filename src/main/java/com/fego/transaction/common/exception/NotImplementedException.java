package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class NotImplementedException extends ServicesException {
    public NotImplementedException(ErrorCode code, String message) {
        super(code, message);
    }
}