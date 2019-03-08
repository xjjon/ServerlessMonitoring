package com.serverless;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionHandler {

    public HttpURLConnection getConnection(String url) throws IOException {
        URL websiteUrl = new URL(url);
        return (HttpURLConnection) websiteUrl.openConnection();
    }
}
