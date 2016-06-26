package com.siavash.messenger;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

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
            case "/user": {
                log.info("POST request with /user url and user data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                User user = gson.fromJson(requestBody, User.class);

                MainApp.insertions.insertUser(
                        user.getUserName(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getPhoneNumber());
                sendOkMessage(httpExchange);
                break;
            }
            case "/msg": {
                log.info("POST request with /msg url and message data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                Message message = gson.fromJson(requestBody, Message.class);

//            MainApp.insertions.insertMessage(
//                    message.getSender(),
//                    message.getContent(),
//                    message.getReceivers());
                sendOkMessage(httpExchange);
                break;
            }
            case "/contact": {
                log.info("POST request with /contact url and user data as parameter");

                String requestBody = Util.readFromInputStream(httpExchange.getRequestBody());
                Contact contact = gson.fromJson(requestBody, Contact.class);

                MainApp.insertions.insertContacts(
                        contact.getParentUserName(),
                        contact.getContactUserName(),
                        contact.getFirstName(),
                        contact.getLastName());
                sendOkMessage(httpExchange);
                break;
            }
        }
    }

    private void sendOkMessage(HttpExchange httpExchange) throws IOException {
        String response = Constants.HTTP_ACCEPTED;
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_ACCEPTED, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
