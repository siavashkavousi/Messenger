package com.siavash.messenger;

import com.google.gson.Gson;
import com.siavash.messenger.data.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by sia on 6/25/16.
 */
public class CustomHttpHandler implements HttpHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomHttpHandler.class);
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String context = httpExchange.getRequestURI().toString();
        String[] uriSegments = context.split("\\?");
        switch (uriSegments[0]) {
            case "/user":
                log.info("GET request with /user url and username as parameter");
                String username = uriSegments[1].split("=")[1];
                log.info("requested username: " + username);

                User user = MainApp.queries.findUser(username);

                user = new User("sia", "siavash", "kavousi", "09213456");
                String response = gson.toJson(user);
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                break;
        }
    }
}
