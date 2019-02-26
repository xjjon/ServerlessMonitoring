package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.serverless.model.CheckRequest;
import com.serverless.model.CheckResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MonitorHandler implements RequestHandler<Map<String, List<CheckRequest>>, String> {

    private static final Logger LOG = LogManager.getLogger(MonitorHandler.class);

    private static final String TOPIC_ARN = "arn:aws:sns:us-east-1:293955204652:monitoring-tool-dev-MonitoringNotificationTopic-6MZ276O8LXWR";
    private static final String MONITORS = "monitors";

    private static AmazonSNS SNS = AmazonSNSClient.builder().build();

    @Override
    public String handleRequest(Map<String, List<CheckRequest>> input, Context context) {
        List<CheckRequest> requests = input.get(MONITORS);
        LOG.info("Received request with {} monitors.", requests.size());
        for (CheckRequest request : requests) {
            checkStatus(request);
        }
        return "Completed check request.";
    }

    private void checkStatus(CheckRequest checkRequest) {
        try {
            URL websiteUrl = new URL(checkRequest.getUrl());
            HttpURLConnection connection = (HttpURLConnection) websiteUrl.openConnection();
            CheckResponse response = getResponse(checkRequest, connection);

            if(response.isValid()) {
                LOG.info("Check succeeded on {}. Response: {}, {}.",
                        checkRequest.getUrl(),
                        response.getStatusCode(),
                        response.getStatusMessage());
            } else {
                String errorMessage = String.format("Check failed on %s. Expected %s. Received %s with message %s.",
                        checkRequest.getUrl(),
                        checkRequest.getExpectedStatusCode(),
                        response.getStatusCode(),
                        response.getStatusMessage());
                LOG.error(errorMessage);
                SNS.publish(TOPIC_ARN, errorMessage);
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
