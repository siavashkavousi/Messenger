package com.siavash.messenger;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;

import java.util.List;

/**
 * Created by sia on 6/25/16.
 */
public final class Insertions {
    private MongoDatabase db = Util.getDatabase();

    private Insertions() {
    }

    public static Insertions newInstance() {
        return new Insertions();
    }

    public void insertUser(String userName, String password,
                           String firstName, String lastName,
                           String phoneNumber) {
        Document doc = new Document("_id", userName)
                .append("password", password)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phoneNumber", phoneNumber);

        MongoCollection<Document> userCollection = db.getCollection(Constants.USER);
        userCollection.insertOne(doc, (result, t) -> Util.logInsertionSuccess(Constants.USER));
    }

    public void insertContact(String clientUserName, String contactUserName,
                              String firstName, String lastName) {
        Document doc = new Document("clientUserName", clientUserName)
                .append("contactUserName", contactUserName)
                .append("firstName", firstName)
                .append("lastName", lastName);

        MongoCollection<Document> contactsCollection = db.getCollection(Constants.CONTACTS);
        contactsCollection.insertOne(doc, (result, t) -> Util.logInsertionSuccess(Constants.CONTACTS));
    }

    public void insertMessage(String clientUserName, String content, List<String> contactsUserName) {
        Document doc = new Document("clientUserName", clientUserName)
                .append("content", content)
                .append("contactsUserName", contactsUserName);

        MongoCollection<Document> messageCollection = db.getCollection(Constants.MESSAGE);
        messageCollection.insertOne(doc, (result, t) -> Util.logInsertionSuccess(Constants.MESSAGE));
    }

    public void insertGroup(String groupId,
                            String creatorId,
                            String name) {
        Document doc = new Document("_id", groupId)
                .append("creator_id", creatorId)
                .append("name", name);

        MongoCollection<Document> groupCollection = db.getCollection(Constants.GROUP);
        groupCollection.insertOne(doc, (result, t) -> Util.logInsertionSuccess(Constants.GROUP));
    }

    public void insertGroupMessage(String clientUserName,
                                   String content,
                                   List<String> contactsUserName,
                                   String groupId) {
        Document doc = new Document("clientUserName", clientUserName)
                .append("content", content)
                .append("contactsUserName", contactsUserName)
                .append("groupId", groupId);

        MongoCollection<Document> messageCollection = db.getCollection(Constants.MESSAGE);
        messageCollection.insertOne(doc, (result, t) -> Util.logInsertionSuccess(Constants.MESSAGE));
    }
}
