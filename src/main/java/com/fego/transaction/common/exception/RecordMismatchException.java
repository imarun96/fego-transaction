package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class RecordMismatchException extends ServicesException {

    private static final long serialVersionUID = 2217271088421466238L;

    public RecordMismatchException(final ErrorCode code, final String message) {
        super(code, message);
    }
}