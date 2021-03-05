package com.fego.transaction.common.exception;

import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * <p>
 * Interface - Generic exception response message.
 * </p>
 *
 * @author Rajasekar Nagarajan created on March 30, 2020
 */
@Component
public class ExceptionResolver {

    public ExceptionDetail resolveError(final HttpStatus statusCode, final ErrorCode messageCode, final String message,
                                        final String error, final String gid) {
        final ExceptionDetail.Builder builder = new ExceptionDetail.Builder();
        builder.setDateTime(ZonedDateTime.now().toInstant().toEpochMilli());
        builder.setResponseCode(statusCode.value());
        builder.setMessage(message);
        builder.setMessageCode(Integer.toString(messageCode.key()));
        builder.setException(error);
        builder.setStatus(gid + Constants.COLON + Constants.FAILED);
        return builder.build();
    }
}