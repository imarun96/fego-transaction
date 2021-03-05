package com.fego.transaction.common.exception;

import java.util.Objects;

/**
 * <p>
 * Generic error response.
 * </p>
 *
 * @author Rajasekar Nagarajan created on March 30, 2020
 */
public class ExceptionDetail {

    private final long dateTime;
    private final String status;
    private final Integer responseCode;
    private final String message;
    private final String messageCode;
    private final String exception;

    public ExceptionDetail(final long dateTime, final String status, final Integer errorCode, final String message,
                           final String messageCode, final String exception) {
        this.dateTime = dateTime;
        this.status = status;
        this.responseCode = errorCode;
        this.message = message;
        this.exception = exception;
        this.messageCode = messageCode;
    }

    public String getStatus() {
        return status;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public String getException() {
        return exception;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public long getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return Boolean.TRUE;
        }
        if (o instanceof ExceptionDetail) {
            final ExceptionDetail error = (ExceptionDetail) o;
            return Objects.equals(getStatus(), error.getStatus()) && getResponseCode() == error.getResponseCode()
                    && Objects.equals(getMessage(), error.getMessage())
                    && Objects.equals(getMessageCode(), error.getMessageCode())
                    && Objects.equals(getException(), error.getException());
        }
        return Boolean.FALSE;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getResponseCode(), getMessage(), getMessageCode(), getException());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // noinspection StringBufferReplaceableByString
        return new StringBuilder().append(getStatus()).toString();
    }

    public static class Builder {

        private long dateTime;
        private String status;
        private Integer responseCode;
        private String message;
        private String messageCode;
        private String exception;

        public Builder() {
            // Builder method to build the exception
        }

        /*
         * Setters
         */
        public Builder setDateTime(final long dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setStatus(final String status) {
            this.status = status;
            return this;
        }

        public Builder setResponseCode(final Integer responseCode) {
            this.responseCode = responseCode;
            return this;
        }

        public Builder setMessage(final String message) {
            this.message = message;
            return this;
        }

        public Builder setMessageCode(final String messageCode) {
            this.messageCode = messageCode;
            return this;
        }

        public Builder setException(final String exception) {
            this.exception = exception;
            return this;
        }

        public ExceptionDetail build() {
            return new ExceptionDetail(this.dateTime, this.status, this.responseCode, this.message, this.messageCode,
                    this.exception);
        }
    }
}