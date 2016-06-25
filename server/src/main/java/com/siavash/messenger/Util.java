package com.siavash.messenger;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;

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

    static MongoDatabase getDatabase() {
        return mongoClient.getDatabase(Constants.DATABASE_NAME);
    }

    private static void printSuccessMessage(String collectionName) {
        System.out.println(collectionName + " collection has been created successfully");
    }

    static void printInsertionSuccess(String collectionName){
        System.out.println("Insertion into " + collectionName + " completed successfully");
    }
}
