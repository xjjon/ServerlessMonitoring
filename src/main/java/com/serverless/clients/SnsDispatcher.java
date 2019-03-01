package com.serverless.clients;

import com.amazonaws.services.sns.AmazonSNS;
import com.serverless.NotificationDispatcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnsDispatcher implements NotificationDispatcher {

    private static final Logger LOG = LogManager.getLogger(SnsDispatcher.class);

    private final AmazonSNS client;
    private final String topicArn;

    public SnsDispatcher(AmazonSNS client, String topicArn) {
        this.client = client;
        this.topicArn = topicArn;
    }

    @Override
    public void dispatch(String message) {
        client.publish(topicArn, message);
        LOG.info("Published {} to topic {}", message, topicArn);
    }
}
