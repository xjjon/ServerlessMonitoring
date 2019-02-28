package com.serverless.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckResponseTest {

    private static final String URL = "http://www.mytesturl.com";
    private static final int STATUS_CODE = 200;
    private static final String STATUS_MESSAGE = "Test Status Message.";

    @Test
    void isValidResponseNullMessage() {
        CheckRequest request = new CheckRequest(URL, STATUS_CODE, null);
        CheckResponse response = new CheckResponse(request, STATUS_CODE, STATUS_MESSAGE);

        Assertions.assertTrue(response.isValid());
    }

    @Test
    void isInvalidResponseNullMessage() {
        CheckRequest request = new CheckRequest(URL, STATUS_CODE, null);
        CheckResponse response = new CheckResponse(request, 500, STATUS_MESSAGE);

        Assertions.assertFalse(response.isValid());
    }

    @Test
    void isValidResponseNullCode() {
        CheckRequest request = new CheckRequest(URL, null, STATUS_MESSAGE);
        CheckResponse response = new CheckResponse(request, STATUS_CODE, STATUS_MESSAGE);

        Assertions.assertTrue(response.isValid());
    }

    @Test
    void isInvalidResponseNullCode() {
        CheckRequest request = new CheckRequest(URL, null, "Test Message");
        CheckResponse response = new CheckResponse(request, 500, STATUS_MESSAGE);

        Assertions.assertFalse(response.isValid());
    }

    @Test
    void isValidResponse() {
        CheckRequest request = new CheckRequest(URL, STATUS_CODE, STATUS_MESSAGE);
        CheckResponse response = new CheckResponse(request, STATUS_CODE, STATUS_MESSAGE);

        Assertions.assertTrue(response.isValid());
    }

    @Test
    void isInvalidResponseInvalidMessage() {
        CheckRequest request = new CheckRequest(URL, STATUS_CODE, STATUS_MESSAGE);
        CheckResponse response = new CheckResponse(request, 500, null);

        Assertions.assertFalse(response.isValid());
    }

    @Test
    void isInvalidResponseInvalidCode() {
        CheckRequest request = new CheckRequest(URL, STATUS_CODE, STATUS_MESSAGE);
        CheckResponse response = new CheckResponse(request, 500, STATUS_MESSAGE);

        Assertions.assertFalse(response.isValid());
    }
}