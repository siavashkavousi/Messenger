package com.siavash.messenger;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sia on 7/1/16.
 */
public class Removals {
    private static final Logger log = LoggerFactory.getLogger(Removals.class);
    private MongoDatabase db = Util.getDatabase();

    private Removals() {
    }

    public static Removals newInstance() {
        return new Removals();
    }

    public void removeContact(String clientUserName,
                              String contactUserName,
                              String firstName,
                              String lastName) {
        Document doc = new Document("clientUserName", clientUserName)
                .append("contactUserName", contactUserName)
                .append("firstName", firstName)
                .append("lastName", lastName);

        MongoCollection<Document> contactsCollection = db.getCollection(Constants.CONTACTS);
        contactsCollection.deleteOne(doc, (result, t) -> Util.logRemovalSuccess(Constants.CONTACTS));
    }
}
