package com.siavash.messenger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by sia on 6/26/16.
 */
public class HttpGetHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpGetHandler.class);
    private Gson gson;

    public HttpGetHandler(Gson gson) {
        this.gson = gson;
    }

    public void handle(HttpExchange httpExchange, String path) throws IOException {
        if (path.equals("/user")) {
            log.info("GET request with /user url and username as parameter");

            Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
            String username = queryMap.get("username");
            log.info("requested username: " + username);
            User user = MainApp.queries.findUser(username);

            user = new User("sia", "siavash", "kavousi", "09213456");
            String response = gson.toJson(user);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else if (path.equals("/msg")) {
            log.info("GET request with /msg url and username as parameter");

            Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
            String username = queryMap.get("username");
            log.info("requested username: " + username);
            User user = MainApp.queries.findUser(username);

            user = new User("sia", "siavash", "kavousi", "09213456");
            String response = gson.toJson(user);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
