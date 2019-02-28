package com.serverless.model;

public class CheckRequest {

    private String url;
    private Integer expectedStatusCode;
    private String expectedResponseMessage;

    public CheckRequest(String url, Integer expectedStatusCode, String expectedResponseMessage) {
        this.url = url;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedResponseMessage = expectedResponseMessage;
    }

    public CheckRequest() { }

    public String getUrl() {
        return url;
    }

    public Integer getExpectedStatusCode() {
        return expectedStatusCode;
    }

    public String getExpectedResponseMessage() {
        return expectedResponseMessage;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setExpectedStatusCode(int expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
    }

    public void setExpectedResponseMessage(String expectedResponseMessage) {
        this.expectedResponseMessage = expectedResponseMessage;
    }
}
