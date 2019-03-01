package com.serverless.clients;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.sns.AmazonSNS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SnsDispatcherTest {

    private static final String TOPIC_ARN = "TOPIC_ARN";
    private static final String TEST_MESSAGE = "Test Message";

    @Mock
    private AmazonSNS mockClient;

    @Test
    void dispatch() {
        SnsDispatcher dispatcher = new SnsDispatcher(mockClient, TOPIC_ARN);
        dispatcher.dispatch(TEST_MESSAGE);
        Mockito.verify(mockClient).publish(TOPIC_ARN, TEST_MESSAGE);
    }

    @Test
    void dispatchException() {
        Mockito.when(mockClient.publish(TOPIC_ARN, TEST_MESSAGE))
                .thenThrow(new AmazonClientException("Exception"));
        SnsDispatcher dispatcher = new SnsDispatcher(mockClient, TOPIC_ARN);

        Assertions.assertThrows(
                AmazonClientException.class,
                () -> dispatcher.dispatch(TEST_MESSAGE));
        Mockito.verify(mockClient).publish(TOPIC_ARN, TEST_MESSAGE);
    }
}