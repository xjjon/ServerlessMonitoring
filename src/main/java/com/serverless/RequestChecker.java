package com.serverless;

import com.serverless.model.CheckRequest;
import com.serverless.model.CheckResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RequestChecker {

    private static final Logger LOG = LogManager.getLogger(RequestChecker.class);

    private final NotificationDispatcher dispatcher;

    public RequestChecker(NotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void checkRequests(List<CheckRequest> requests) {
        for (CheckRequest request : requests) {
            checkStatus(request);
        }
    }

    private void checkStatus(CheckRequest checkRequest) {
        try {
            URL websiteUrl = new URL(checkRequest.getUrl());
            HttpURLConnection connection = (HttpURLConnection) websiteUrl.openConnection();
            CheckResponse response = getResponse(checkRequest, connection);

            if (response.isValid()) {
                LOG.info("Check succeeded on {}. Response: {}, {}.",
                        checkRequest.getUrl(),
                        response.getStatusCode(),
                        response.getStatusMessage());
            } else {
                String errorMessage = String.format("Check failed on %s. Expected %s - %s. Received %s with response %s.",
                        checkRequest.getUrl(),
                        checkRequest.getExpectedStatusCode(),
                        checkRequest.getExpectedResponseMessage(),
                        response.getStatusCode(),
                        response.getStatusMessage());
                LOG.error(errorMessage);
                dispatcher.dispatch(errorMessage);
            }

        } catch (MalformedURLException e) {
            LOG.error("Malformed URL: " + checkRequest.getUrl(), e);
        } catch (IOException e) {
            LOG.error("IO Exception at {}, ", checkRequest.getUrl(), e);
        }
    }

    private CheckResponse getResponse(CheckRequest request, HttpURLConnection connection) throws IOException {
        return new CheckResponse(request, connection.getResponseCode(), connection.getResponseMessage());
    }
}
