package com.fego.transaction.common.exception;

import com.fego.transaction.common.enumeration.ErrorCode;

/**
 * <p>
 * User defined exception invalid data is exist throw an error.
 * </p>
 *
 * @author Rajasekar Nagarajan created on March 26, 2020
 */
public class InvalidDataException extends ServicesException {

    public InvalidDataException(final ErrorCode code, final String message) {
        super(code, message);
    }
}