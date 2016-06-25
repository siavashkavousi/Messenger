package database;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.Constants;

/**
 * Created by sia on 6/25/16.
 */
public class InsertionQueries {

    public void insertUser(String firstName, String lastName, String phoneNumber) {
        Document doc = new Document("firstName", firstName)
                .append("lastName", lastName)
                .append("phoneNumber", phoneNumber);

        MongoDatabase db = Util.getDatabase();
        MongoCollection<Document> userCollection = db.getCollection(Constants.USER);
        userCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.USER));
    }

    public void insertContacts(ObjectId parentUserId, ObjectId userId) {
        Document doc = new Document("_id", new Document("parentUserId", parentUserId)
                .append("userId", userId));

        MongoDatabase db = Util.getDatabase();
        MongoCollection<Document> contactsCollection = db.getCollection(Constants.CONTACTS);
        contactsCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.CONTACTS));
    }

    public void insertMessage(ObjectId senderId, String content, ObjectId... receiversId) {
        Document doc = new Document("sender_id", senderId)
                .append("content", content);

        Document innerDocument = new Document();
        for (int i = 0; i < receiversId.length; i++)
            innerDocument.append("receiver_id" + i, receiversId[i]);
        doc.append("receivers", innerDocument);

        MongoDatabase db = Util.getDatabase();
        MongoCollection<Document> messageCollection = db.getCollection(Constants.MESSAGE);
        messageCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.MESSAGE));
    }

    public void insertGroup(ObjectId creatorId, String title, String groupId) {
        Document doc = new Document("creator_id", creatorId)
                .append("title", title)
                .append("group_id", groupId);

        MongoDatabase db = Util.getDatabase();
        MongoCollection<Document> groupCollection = db.getCollection(Constants.GROUP);
        groupCollection.insertOne(doc, (result, t) -> Util.printInsertionSuccess(Constants.GROUP));
    }
}
