package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class InvalidFileException extends ServicesException {

    public InvalidFileException(final ErrorCode code, final String message) {
        super(code, message);
    }
}