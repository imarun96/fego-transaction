package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class RecordNotFoundException extends ServicesException {

    public RecordNotFoundException(final ErrorCode code, final String message) {
        super(code, message);
    }
}