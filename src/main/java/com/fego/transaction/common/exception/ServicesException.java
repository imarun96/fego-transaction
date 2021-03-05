package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

/**
 * <p>
 * Common Service exception handler toa added the exception code and read the
 * message from property using message validator.
 * </p>
 *
 * @author Rajasekar Nagarajan created on March 26, 2020
 */
public class ServicesException extends RuntimeException {

    private static final long serialVersionUID = 6918269662648545345L;
    private final ErrorCode code;
    private final String message;

    public ServicesException(final ErrorCode code, final String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorCode getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}