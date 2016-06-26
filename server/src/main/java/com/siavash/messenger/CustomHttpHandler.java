package com.siavash.messenger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by sia on 6/25/16.
 */
public class CustomHttpHandler implements HttpHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomHttpHandler.class);
    private Gson gson = new Gson();
    private HttpGetHandler httpGetHandler = new HttpGetHandler(gson);
    private HttpPostHandler httpPostHandler = new HttpPostHandler(gson);

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        if (httpExchange.getRequestMethod().equals("GET")) {
            httpGetHandler.handle(httpExchange, path);
        } else if (httpExchange.getRequestMethod().equals("POST")) {
            httpPostHandler.handle(httpExchange, path);
        }
    }
}
