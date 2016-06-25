package com.siavash.messenger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sia on 6/25/16.
 */
public class CustomHttpHandler implements HttpHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomHttpHandler.class);
    private Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        if (httpExchange.getRequestMethod().equals("GET")) {
            switch (path) {
                case "/user":
                    log.info("GET request with /user url and username as parameter");

                    Map<String, String> queryMap = queryToMap(httpExchange.getRequestURI().getQuery());
                    String username = queryMap.get("username");
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
        } else if (httpExchange.getRequestMethod().equals("POST")) {
            String requestBody;
            switch (path) {
                case "/user":
                    log.info("POST request with /user url and user data as parameter");

                    requestBody = readFromInputStream(httpExchange.getRequestBody());
                    User user = gson.fromJson(requestBody, User.class);

                    MainApp.insertions.insertUser(
                            user.getUserName(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getPhoneNumber());
                    httpExchange.sendResponseHeaders(200, 0);
                    break;
                case "/msg":
                    log.info("POST request with /msg url and message data as parameter");

                    requestBody = readFromInputStream(httpExchange.getRequestBody());
                    Message message = gson.fromJson(requestBody, Message.class);

                    System.out.println(message.toString());
//                    MainApp.insertions.insertMessage(
//                            message.getSender(),
//                            message.getContent(),
//                            message.getReceivers());
                    httpExchange.sendResponseHeaders(200, 5);
                    break;
            }
        }
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    private String readFromInputStream(InputStream inputStream) {
        String result = null, line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
