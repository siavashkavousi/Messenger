package com.siavash.messenger;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.sun.net.httpserver.HttpExchange;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sia on 6/25/16.
 */
class Util {
    private static final Logger log = LoggerFactory.getLogger(Util.class);
    public static MongoClient mongoClient = MongoClients.create("mongodb://localhost");

    public static void createCollections() {
        MongoDatabase db = getDatabase();
        db.createCollection(Constants.USER, (result, t) -> printSuccessMessage(Constants.USER));
        db.createCollection(Constants.CONTACTS, (result, t) -> printSuccessMessage(Constants.CONTACTS));
        db.createCollection(Constants.MESSAGE, (result, t) -> printSuccessMessage(Constants.MESSAGE));
//        db.createCollection(Constants.GROUP, (result, t) -> printSuccessMessage(Constants.GROUP));
//        db.createCollection(Constants.GROUP_MEMBERS, (result, t) -> printSuccessMessage(Constants.GROUP_MEMBERS));
//        db.createCollection(Constants.GROUP_MESSAGES, (result, t) -> printSuccessMessage(Constants.GROUP_MESSAGES));
//        db.createCollection(Constants.CHANNEL, (result, t) -> printSuccessMessage(Constants.CHANNEL));
//        db.createCollection(Constants.CHANNEL_MEMBERS, (result, t) -> printSuccessMessage(Constants.CHANNEL_MEMBERS));
//        db.createCollection(Constants.CHANNEL_MESSAGES, (result, t) -> printSuccessMessage(Constants.CHANNEL_MESSAGES));
    }

    public static void createIndex(MongoDatabase db) {
        MongoCollection<Document> userCollection = db.getCollection(Constants.USER);
        userCollection.createIndex(new Document("clientUserName", 1).append("contactUserName", 1)
                , new IndexOptions().unique(true), (result, t) -> log.info("db createIndex: user -> success"));
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
        log.info(collectionName + " collection has been created successfully");
    }

    static void logInsertionSuccess(String collectionName) {
        log.info("Insertion into " + collectionName + " completed successfully");
    }
}
