package com.fego.transaction.common.exception;

import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.common.enumeration.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;

@Component
public class ExceptionResponseCreator {

    private static final String MESSAGE_FOLLOWING_REASON = "Failed to process the request for the following reason : ";
    private static final String MESSAGE_NEW_LINE = "\r\n\t";
    private static final Logger logger = LogManager.getLogger(ExceptionResponseCreator.class);

    Environment environment;
    ExceptionResolver exceptionResolver;

    public ExceptionResponseCreator(Environment environment, ExceptionResolver exceptionResolver) {
        this.environment = environment;
        this.exceptionResolver = exceptionResolver;
    }

    /**
     * Util Function to create response entity to be used by
     * TribetterExceptionHandler and Authentication Filters
     * unsuccessfulAuthentication overrides.
     *
     * @param status
     * @param errorCode
     * @param exception
     * @return
     */
    public ResponseEntity<Object> getExceptionResponseEntity(HttpStatus status, ErrorCode errorCode,
                                                             Exception exception, String message) {
        String uuid = getUUId();
        String errorAsString = getErrorStack(exception).replace(MESSAGE_NEW_LINE, " ");
        logger.error("{} {} {} {} {}", uuid, Constants.COLON, MESSAGE_FOLLOWING_REASON, Constants.NEW_LINE, errorAsString);
        ExceptionResolver resolver = new ExceptionResolver();
        String errorStack = Boolean.TRUE.equals(isProdProfile()) ? "" : errorAsString;
        return new ResponseEntity<>(resolver.resolveError(status, errorCode, message, errorStack, uuid), status);
    }

    public ResponseEntity<Object> getExceptionResponseEntity(HttpStatus status, ErrorCode errorCode,
                                                             Exception exception) {
        return getExceptionResponseEntity(status, errorCode, exception, exception.getMessage());
    }

    /**
     * <p>
     * Generate Java Util UUID
     * </p>
     *
     * @return UUID as string
     */
    public String getUUId() {
        return UUID.randomUUID().toString().replace(Constants.HYPHEN, "");
    }

    /**
     * <p>
     * Convert exception stack trace in to string and return
     * </p>
     *
     * @param ex - Used to convert as string
     * @return Stack value as string
     */
    public String getErrorStack(Exception ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    private Boolean isProdProfile() {
        for (String profile : environment.getActiveProfiles()) {
            if (profile.equals(Constants.PROFILE_ACTIVE_PRODUCTION)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}