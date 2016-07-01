package com.siavash.messenger;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sia on 7/1/16.
 */
public class Updates {
    private static final Logger log = LoggerFactory.getLogger(Updates.class);
    private MongoDatabase db = Util.getDatabase();

    private Updates() {
    }

    public static Updates newInstance() {
        return new Updates();
    }

    public void updateUser(String userName, String newPassword,
                           String newFirstName, String newLastName, String newPhoneNumber) {
        Document doc = new Document("_id", userName);

        log.info("pass: " + newPassword);
        Document newDoc = new Document("$set", new Document("password", newPassword)
                .append("firstName", newFirstName)
                .append("lastName", newLastName)
                .append("phoneNumber", newPhoneNumber));

        MongoCollection<Document> userCollection = db.getCollection(Constants.USER);
        userCollection.updateOne(doc, newDoc, (result, t) -> {
            log.info("db update: updateUser -> modified documents: #" + result.getModifiedCount());
            Util.logUpdateSuccess(Constants.USER);
        });
    }
}
