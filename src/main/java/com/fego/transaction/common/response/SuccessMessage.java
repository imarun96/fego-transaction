package com.fego.transaction.common.response;

import com.fego.transaction.common.constants.Constants;

/**
 * <p>
 * Generic success message object.
 * </p>
 *
 * @param <T> -
 * @author Rajasekar Nagarajan created on March 30, 2020
 */
public class SuccessMessage<T> {
    private String message;
    private Object entity;
    private Integer responseCode;
    private String status;

    /**
     * <h1>Success message.</h1>
     *
     * @param message      - Message to be displayed to the user is passed in this attribute.
     * @param entity       - object is passed in this attribute.
     * @param responseCode - response code is passed in this attribute.
     * @author Jenis
     */
    public SuccessMessage(String message, Object entity, Integer responseCode) {
        this.setMessage(message);
        this.setEntity(entity);
        this.setResponseCode(responseCode);
        this.setStatus(Constants.SUCCESS);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}