package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

public class OperationNotAllowed extends ServicesException {

    public OperationNotAllowed(ErrorCode code, String message) {
        super(code, message);
    }
}