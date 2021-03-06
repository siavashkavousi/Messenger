package com.siavash.messenger;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by sia on 6/25/16.
 */
public class MainApp {
    public static Queries queries = Queries.newInstance();
    public static Insertions insertions = Insertions.newInstance();
    public static Updates updates = Updates.newInstance();
    public static Removals removals = Removals.newInstance();

    public static void main(String[] args) throws IOException {
        CustomHttpHandler httpHandler = new CustomHttpHandler();
        HttpServer server = HttpServer.create(new InetSocketAddress(8100), 0);
        server.createContext("/sign_in", httpHandler);
        server.createContext("/sign_up", httpHandler);
        server.createContext("/user", httpHandler);
        server.createContext("/update_user", httpHandler);
        server.createContext("/delete_contact", httpHandler);
        server.createContext("/msg", httpHandler);
        server.createContext("/contacts", httpHandler);
        server.createContext("/contact", httpHandler);
        server.createContext("/find_contact", httpHandler);
        server.createContext("/find_contacts", httpHandler);
        server.createContext("/report_user", httpHandler);

        server.createContext("/groups", httpHandler);
        server.createContext("/create_group", httpHandler);
        server.createContext("/group_messages", httpHandler);
        server.createContext("/group_members", httpHandler);
        server.setExecutor(null);
        server.start();
        System.out.println("waiting for request");
    }
}
