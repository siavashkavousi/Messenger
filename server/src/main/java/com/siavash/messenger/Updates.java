package com.siavash.messenger;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by sia on 7/1/16.
 */
public class Updates {
    private static final Logger log = LoggerFactory.getLogger(Updates.class);
    private MongoDatabase db = Util.getDatabase();
    private MongoCollection<Document> userCollection = db.getCollection(Constants.USER);
    private MongoCollection<Document> groupCollection = db.getCollection(Constants.GROUP);

    private Updates() {
    }

    public static Updates newInstance() {
        return new Updates();
    }

    public void updateUser(String userName, String newPassword,
                           String newFirstName, String newLastName, String newPhoneNumber) {
        Document doc = new Document("_id", userName);

        Document newDoc = new Document("$set", new Document("password", newPassword)
                .append("firstName", newFirstName)
                .append("lastName", newLastName)
                .append("phoneNumber", newPhoneNumber));

        userCollection.updateOne(doc, newDoc, (result, t) -> {
            log.info("db update: updateUser -> modified documents: #" + result.getModifiedCount());
            Util.logUpdateSuccess(Constants.USER);
        });
    }

    public void incrementUserReports(String userName) {
        Document doc = new Document("_id", userName);

        Document newDoc = new Document("$inc", new Document("num_of_reports", 1));

        userCollection.updateOne(doc, newDoc, (result, t) -> {
            log.info("db update: incrementUserReports -> modified documents: #" + result.getModifiedCount());
            Util.logUpdateSuccess(Constants.USER);
        });
    }

    public void insertGroupMember(String groupId,
                                  List<String> membersUserName) {
        Document doc = new Document("_id", groupId);

        Document newDoc = new Document("$addToSet", new Document("members", membersUserName));

        groupCollection.updateOne(doc, newDoc, (result, t) -> {
            log.info("db update: incrementUserReports -> modified documents: #" + result.getModifiedCount());
            Util.logUpdateSuccess(Constants.USER);
        });
    }
}
