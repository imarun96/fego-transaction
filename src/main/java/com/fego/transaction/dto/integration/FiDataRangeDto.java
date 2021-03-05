package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonPropertyOrder({"from", "to"})
public class FiDataRangeDto {
    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}