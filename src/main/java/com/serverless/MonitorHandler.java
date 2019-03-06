package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.serverless.clients.SnsDispatcher;
import com.serverless.model.CheckRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MonitorHandler implements RequestHandler<Map<String, List<CheckRequest>>, String> {

    private static final Logger LOG = LogManager.getLogger(MonitorHandler.class);

    private static final String MONITORS = "monitors";

    private static AmazonSNS SNS = AmazonSNSClient.builder().build();
    private NotificationDispatcher dispatcher;

    @Override
    public String handleRequest(Map<String, List<CheckRequest>> input, Context context) {
        List<CheckRequest> requests = input.get(MONITORS);
        LOG.info("Received request with {} monitors.", requests.size());
        
        dispatcher = new SnsDispatcher(SNS, System.getenv("topicArn"));
        
        RequestChecker requestChecker = new RequestChecker(dispatcher);
        requestChecker.checkRequests(requests);

        return "Completed check request.";
    }
}
