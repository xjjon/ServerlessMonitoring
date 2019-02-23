package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MonitoringHandler implements RequestHandler<CheckRequest, String> {

    private static final Logger LOG = LogManager.getLogger(MonitoringHandler.class);
    private static final String TOPIC_ARN = "arn:aws:sns:us-east-1:293955204652:monitoring-tool-dev-MonitoringNotificationTopic-6MZ276O8LXWR";
    private static AmazonSNS SNS = AmazonSNSClient.builder().build();

    @Override
    public String handleRequest(CheckRequest request, Context context) {
        LOG.info("Received check request");
        checkStatus(request);
        return "Completed check request";
    }

    private void checkStatus(CheckRequest checkRequest) {
        try {
            URL websiteUrl = new URL(checkRequest.getUrl());
            HttpURLConnection connection = (HttpURLConnection) websiteUrl.openConnection();
            if(connection.getResponseCode() == checkRequest.getExpectedStatusCode()) {
                LOG.info("Check succeeded on {}. Response: {}, {}",checkRequest.getUrl(), connection.getResponseCode(), connection.getResponseMessage());
            } else {
                String errorMessage = String.format("Check failed on %s. Expected %s. Received %s with message %s.",
                        checkRequest.getUrl(),
                        checkRequest.getExpectedStatusCode(),
                        connection.getResponseCode(),
                        connection.getResponseMessage());
                LOG.error(errorMessage);
                SNS.publish(TOPIC_ARN, errorMessage);
            }
        } catch (MalformedURLException e) {
            LOG.error("Malformed URL: " + checkRequest.getUrl(), e);
        } catch (IOException e) {
            LOG.error(e + checkRequest.getUrl());
        }
    }
}
