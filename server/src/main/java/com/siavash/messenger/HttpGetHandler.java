package com.siavash.messenger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by sia on 6/26/16.
 */
public class HttpGetHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpGetHandler.class);
    private Gson gson;

    public HttpGetHandler(Gson gson) {
        this.gson = gson;
    }

    public void handle(HttpExchange httpExchange, String path) throws IOException, ExecutionException, InterruptedException {
        if (path.equals("/user")) {
            log.info("GET request with /user url and username as parameter");

            Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
            String username = queryMap.get("username");

            CompletableFuture<User> futureUser = MainApp.queries
                    .findUser(username);
            User user = futureUser.get();

            String response;
            if (user != null)
                response = gson.toJson(user);
            else
                response = "not found";
            Util.sendResponseMessage(httpExchange, response);
        } else if (path.equals("/msg")) {
            log.info("GET request with /msg url and client_username, contact_username as parameters");

            Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
            String clientUserName = queryMap.get("client_username");
            String contactUserName = queryMap.get("contact_username");

            CompletableFuture<List<Message>> futureMessages = MainApp.queries
                    .findMessages(clientUserName, contactUserName);
            List<Message> messages = futureMessages.get();

            String response = gson.toJson(messages);
            Util.sendResponseMessage(httpExchange, response);
        } else if (path.equals("/contact")) {
            log.info("GET request with /contact url and client_username as parameters");

            Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
            String clientUserName = queryMap.get("client_username");

            CompletableFuture<List<Contact>> futureContacts = MainApp.queries
                    .findContacts(clientUserName);
            List<Contact> contacts = futureContacts.get();

            String response = gson.toJson(contacts);
            Util.sendResponseMessage(httpExchange, response);
        } else if (path.equals("/sign_in")) {
            log.info("GET request with /sign_in url and client username and password as parameters");

            Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
            String userName = queryMap.get("username");
            String password = queryMap.get("password");

            CompletableFuture<User> futureContacts = MainApp.queries
                    .signInUser(userName, password);
            User user = futureContacts.get();

            String response = gson.toJson(user);
            Util.sendResponseMessage(httpExchange, response);
        }
    }
}
