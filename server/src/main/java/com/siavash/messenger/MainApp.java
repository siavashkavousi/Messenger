package com.siavash.messenger;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by sia on 6/25/16.
 */
public class MainApp {
    public static Queries queries = Queries.newInstance();
    public static Insertions insertions = Insertions.newInstance();
    public static MongoClient mongoClient = MongoClients.create("mongodb://localhost");

    public static void main(String[] args) throws IOException {
        CustomHttpHandler httpHandler = new CustomHttpHandler();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/user", httpHandler);
        server.createContext("/msg", httpHandler);
        server.setExecutor(null);
        server.start();
        System.out.println("waiting for request");
    }
}
