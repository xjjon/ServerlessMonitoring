package com.serverless;

public class CheckRequest {

    private String url;
    private int expectedStatusCode;

    public CheckRequest(String url, int expectedStatusCode) {
        this.url = url;
        this.expectedStatusCode = expectedStatusCode;
    }

    public CheckRequest() { }

    public String getUrl() {
        return url;
    }

    public int getExpectedStatusCode() {
        return expectedStatusCode;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setExpectedStatusCode(int expectedStatusCode) {
        this.expectedStatusCode = expectedStatusCode;
    }
}
