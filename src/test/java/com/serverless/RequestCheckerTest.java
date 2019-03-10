package com.serverless;

import com.serverless.model.CheckRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestCheckerTest {

    @Mock
    private NotificationDispatcher dispatcher;

    @Mock
    private ConnectionHandler connectionHandler;

    @Mock
    private HttpURLConnection httpURLConnection;

    @Test
    void checkRequestSuccess() throws Exception {
        RequestChecker checker = new RequestChecker(dispatcher, connectionHandler);

        CheckRequest request = new CheckRequest("test-url.com", 200, "response message");
        List<CheckRequest> requests = new ArrayList<>();
        requests.add(request);

        when(httpURLConnection.getResponseCode()).thenReturn(200);
        when(httpURLConnection.getResponseMessage()).thenReturn("response message");

        when(connectionHandler.getConnection("test-url.com")).thenReturn(httpURLConnection);

        checker.checkRequests(requests);

        Mockito.verify(dispatcher, never()).dispatch(anyString());
    }
}