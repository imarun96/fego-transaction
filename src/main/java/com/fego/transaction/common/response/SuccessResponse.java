package com.fego.transaction.common.response;

import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.paged.OneBasedIndexedPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * Generic success response
 * </p>
 *
 * @param <T>
 * @author Rajasekar Nagarajan created on March 30, 2020
 */
public class SuccessResponse<T> extends ResponseEntity<Object> {

    public SuccessResponse(String message, HttpStatus responseCode) {
        this(message, new EmptyJsonResponse(), responseCode);
    }

    public SuccessResponse(OneBasedIndexedPage<T> entity, HttpStatus responseCode) {
        this(Constants.OK, entity, responseCode);
    }

    public SuccessResponse(Object entity, HttpStatus responseCode) {
        this(Constants.OK, entity, responseCode);
    }

    public SuccessResponse(HttpStatus responseCode) {
        this(Constants.OK, new EmptyJsonResponse(), responseCode);
    }

    public SuccessResponse(String message, Object entity, HttpStatus httpStatus) {
        super(new SuccessMessage<T>(message, entity, httpStatus.value()), httpStatus);
    }
}