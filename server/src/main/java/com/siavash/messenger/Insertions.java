package com.siavash.messenger;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

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

    public void insertUser(String userName, String firstName, String lastName, String phoneNumber) {
        Document doc = new Document("_id", userName)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("phoneNumber", phoneNumber);

        MongoCollection<Document> userCollection = db.getCollection(Constants.USER);
        userCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.USER));
    }

    public void insertContacts(String clientUserName, String contactUserName,
                               String firstName, String lastName) {
        Document doc = new Document("clientUserName", clientUserName)
                .append("contactUserName", contactUserName)
                .append("firstName", firstName)
                .append("lastName", lastName);

        MongoCollection<Document> contactsCollection = db.getCollection(Constants.CONTACTS);
        contactsCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.CONTACTS));
    }

    public void insertMessage(String clientUserName, String content, List<String> contactsUserName) {
        Document doc = new Document("clientUserName", clientUserName)
                .append("content", content)
                .append("contactsUserName", contactsUserName);

        MongoCollection<Document> messageCollection = db.getCollection(Constants.MESSAGE);
        messageCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.MESSAGE));
    }

    public void insertGroup(ObjectId creatorId, String title, String groupId) {
        Document doc = new Document("creator_id", creatorId)
                .append("title", title)
                .append("group_id", groupId);

        MongoCollection<Document> groupCollection = db.getCollection(Constants.GROUP);
        groupCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.GROUP));
    }
}
