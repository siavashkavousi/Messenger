package com.siavash.messenger;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sia on 6/25/16.
 */
class Util {
    private static MongoClient mongoClient = MongoClients.create();

    public static void createCollections() {
        MongoDatabase db = getDatabase();
        db.createCollection(Constants.USER, (result, t) -> printSuccessMessage(Constants.USER));
        db.createCollection(Constants.CONTACTS, (result, t) -> printSuccessMessage(Constants.CONTACTS));
        db.createCollection(Constants.MESSAGE, (result, t) -> printSuccessMessage(Constants.MESSAGE));
        db.createCollection(Constants.GROUP, (result, t) -> printSuccessMessage(Constants.GROUP));
        db.createCollection(Constants.GROUP_MEMBERS, (result, t) -> printSuccessMessage(Constants.GROUP_MEMBERS));
        db.createCollection(Constants.GROUP_MESSAGES, (result, t) -> printSuccessMessage(Constants.GROUP_MESSAGES));
        db.createCollection(Constants.CHANNEL, (result, t) -> printSuccessMessage(Constants.CHANNEL));
        db.createCollection(Constants.CHANNEL_MEMBERS, (result, t) -> printSuccessMessage(Constants.CHANNEL_MEMBERS));
        db.createCollection(Constants.CHANNEL_MESSAGES, (result, t) -> printSuccessMessage(Constants.CHANNEL_MESSAGES));
    }

    public static void sendResponseMessage(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static Map<String, String> queryToMap(String query) {
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

    public static String readFromInputStream(InputStream inputStream) {
        String result = "", line;
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

    static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(Constants.DATABASE_NAME);
    }

    private static void printSuccessMessage(String collectionName) {
        System.out.println(collectionName + " collection has been created successfully");
    }

    static void printInsertionSuccess(String collectionName) {
        System.out.println("Insertion into " + collectionName + " completed successfully");
    }
}
