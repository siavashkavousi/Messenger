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
        switch (path) {
            case "/user": {
                log.info("GET request with /user url and username as parameter");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String username = queryMap.get("username");

                CompletableFuture<User> futureUser = MainApp.queries
                        .findUser(username);
                User user = futureUser.get();

                String response = gson.toJson(user);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/msg": {
                log.info("GET request with /msg url and client_username, contact_username as parameters");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String clientUserName = queryMap.get("client_username");
                String contactUserName = queryMap.get("contact_username");

                CompletableFuture<List<Message>> futureMessages = MainApp.queries
                        .findMessages(clientUserName, contactUserName);
                List<Message> messages = futureMessages.get();

                String response = gson.toJson(messages);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/contacts": {
                log.info("GET request with /contact url and client_username as parameters");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String clientUserName = queryMap.get("client_username");

                CompletableFuture<List<Contact>> futureContacts = MainApp.queries
                        .findContacts(clientUserName);
                List<Contact> contacts = futureContacts.get();

                String response = gson.toJson(contacts);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/sign_in": {
                log.info("GET request with /sign_in url and client username and password as parameters");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String userName = queryMap.get("username");
                String password = queryMap.get("password");

                CompletableFuture<User> futureUser = MainApp.queries
                        .signInUser(userName, password);
                User user = futureUser.get();

                String response = gson.toJson(user);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/find_contacts": {
                log.info("GET request with /find_contacts url and client username and name as parameters");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String userName = queryMap.get("client_username");
                String name = queryMap.get("name");

                CompletableFuture<List<Contact>> futureContacts = MainApp.queries
                        .findContactWithName(userName, name);
                List<Contact> contacts = futureContacts.get();

                String response = gson.toJson(contacts);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/find_contact": {
                log.info("GET request with /find_contact url and client and contact username as parameters");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String userName = queryMap.get("client_username");
                String contactUsername = queryMap.get("contact_username");

                CompletableFuture<Contact> futureContacts = MainApp.queries
                        .findContact(userName, contactUsername);
                Contact contact = futureContacts.get();

                String response = gson.toJson(contact);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/group_messages": {
                log.info("GET request with /msg_group url and client_username and group_id and except_client as parameters");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String userName = queryMap.get("client_username");
                String groupId = queryMap.get("group_id");
                boolean exceptClient = Boolean.parseBoolean(queryMap.get("except_client"));

                CompletableFuture<List<Message>> futureMessages = MainApp.queries
                        .findGroupMessages(
                                userName,
                                groupId,
                                exceptClient);
                List<Message> messages = futureMessages.get();

                String response = gson.toJson(messages);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/groups": {
                log.info("GET request with /groups url and client_username as parameters");

                Map<String, String> queryMap = Util.queryToMap(httpExchange.getRequestURI().getQuery());
                String userName = queryMap.get("client_username");

                CompletableFuture<List<Group>> futureMessages = MainApp.queries
                        .findGroups(userName);
                List<Group> groups = futureMessages.get();

                String response = gson.toJson(groups);
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
        }
    }
}
