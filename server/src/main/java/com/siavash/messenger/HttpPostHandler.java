package com.siavash.messenger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by sia on 6/26/16.
 */
public class HttpPostHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpPostHandler.class);
    private Gson gson;

    public HttpPostHandler(Gson gson) {
        this.gson = gson;
    }

    public void handle(HttpExchange httpExchange, String path) throws IOException {
        switch (path) {
            case "/sign_up": {
                log.info("POST request with /sign_up url and user data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                User user = gson.fromJson(requestBody, User.class);

                MainApp.insertions.insertUser(
                        user.getUserName(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhoneNumber());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/msg": {
                log.info("POST request with /msg url and message data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                Message message = gson.fromJson(requestBody, Message.class);

                MainApp.insertions.insertMessage(
                        message.getClient(),
                        message.getContent(),
                        message.getContacts());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/contact": {
                log.info("POST request with /contact url and contact data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                Contact contact = gson.fromJson(requestBody, Contact.class);

                MainApp.insertions.insertContact(
                        contact.getClientUserName(),
                        contact.getContactUserName(),
                        contact.getFirstName(),
                        contact.getLastName());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/update_user": {
                log.info("POST request with /update_user url and user data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                User user = gson.fromJson(requestBody, User.class);

                MainApp.updates.updateUser(
                        user.getUserName(),
                        user.getPassword(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhoneNumber());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/delete_contact": {
                log.info("POST request with /delete_contact url and contact data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                Contact contact = gson.fromJson(requestBody, Contact.class);

                MainApp.removals.removeContact(
                        contact.getClientUserName(),
                        contact.getContactUserName(),
                        contact.getFirstName(),
                        contact.getLastName());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/report_user": {
                log.info("POST request with /report_user url and username as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                Request request = gson.fromJson(requestBody, Request.class);

                MainApp.updates.incrementUserReports(request.getMessage());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/create_group": {
                log.info("POST request with /create_group url and group data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                Group group = gson.fromJson(requestBody, Group.class);

                MainApp.insertions.insertGroup(
                        group.getGroupId(),
                        group.getCreatorId(),
                        group.getName());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
            case "/group_members": {
                log.info("POST request with /group_member url and users data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                GroupMembers members = gson.fromJson(requestBody, GroupMembers.class);

                MainApp.updates.insertGroupMember(
                        members.getGroupId(),
                        members.getMembersUserName());

                String response = gson.toJson(new Response("200", Constants.HTTP_ACCEPTED));
                Util.sendResponseMessage(httpExchange, response);
                break;
            }
        }
    }
}
