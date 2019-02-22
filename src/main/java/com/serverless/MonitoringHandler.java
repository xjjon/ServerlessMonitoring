package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MonitoringHandler implements RequestHandler<CheckRequest, String> {

    private static final Logger LOG = LogManager.getLogger(MonitoringHandler.class);

    @Override
    public String handleRequest(CheckRequest request, Context context) {
        LOG.info("Received check request");
        checkStatus(request.getUrl());
        return "Completed check request";
    }

    private void checkStatus(String url) {
        try {
            URL websiteUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) websiteUrl.openConnection();
            LOG.info(connection.getResponseCode() + " " + connection.getResponseMessage());
        } catch (MalformedURLException e) {
            LOG.error("Malformed URL: " + url, e);
        } catch (IOException e) {
            LOG.error(e);
        }

    }
}
