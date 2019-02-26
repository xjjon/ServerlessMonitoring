package com.serverless.model;

public class CheckResponse {

    private CheckRequest checkRequest;
    private int statusCode;
    private String statusMessage;

    public CheckResponse(CheckRequest checkRequest, int statusCode, String statusMessage) {
        this.checkRequest = checkRequest;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public boolean isValid() {
        return getCheckRequest().getExpectedStatusCode() == statusCode;
    }

    public CheckRequest getCheckRequest() {
        return checkRequest;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
