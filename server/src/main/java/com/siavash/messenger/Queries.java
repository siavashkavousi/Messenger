package com.siavash.messenger;

import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by sia on 6/25/16.
 */
public class Queries {
    private static final Logger log = LoggerFactory.getLogger(Queries.class);
    private MongoDatabase db = Util.getDatabase();
    private MongoCollection<Document> userCollection = db.getCollection(Constants.USER);
    private MongoCollection<Document> contactsCollection = db.getCollection(Constants.CONTACTS);
    private MongoCollection<Document> messageCollection = db.getCollection(Constants.MESSAGE);

    private Queries() {
    }

    public static Queries newInstance() {
        return new Queries();
    }

    public CompletableFuture<User> signInUser(String userName, String password) {
        CompletableFuture<User> futureUser = new CompletableFuture<>();
        userCollection.find(new Document("_id", userName).append("password", password)).into(new ArrayList<>(),
                (result, t) -> {
                    log.info("db query: signInUser -> found documents: #" + result.size());
                    List<User> users = convertDocumentsToUsers(result);
                    if (users.size() != 0)
                        futureUser.complete(users.get(0));
                    else
                        futureUser.complete(null);
                });
        return futureUser;
    }

    public CompletableFuture<User> findUser(String userName) {
        CompletableFuture<User> futureUser = new CompletableFuture<>();
        // // FIXME: 6/29/16 how to change the arraylist to single item!!!
        userCollection.find(new Document("_id", userName)).into(new ArrayList<>(),
                (result, t) -> {
                    log.info("db query: findUser -> found documents: #" + result.size());
                    List<User> users = convertDocumentsToUsers(result);
                    if (users.size() != 0)
                        futureUser.complete(users.get(0));
                    else
                        futureUser.complete(null);
                });
        return futureUser;
    }

    public CompletableFuture<List<Contact>> findContacts(String clientUserName) {
        CompletableFuture<List<Contact>> futureContacts = new CompletableFuture<>();
        contactsCollection.find(new Document("clientUserName", clientUserName)).into(new ArrayList<>(),
                (result, t) -> {
                    log.info("db query: findContacts -> found documents: #" + result.size());
                    List<Contact> contacts = convertDocumentsToContacts(result);
                    futureContacts.complete(contacts);
                });
        return futureContacts;
    }

    public CompletableFuture<List<Message>> findMessages(String clientUserName, String contactUserName) {
        CompletableFuture<List<Message>> futureMessages = new CompletableFuture<>();
        messageCollection.find(new Document("clientUserName", clientUserName)
                .append("contactsUserName", contactUserName)).into(new ArrayList<>(),
                (result, t) -> {
                    log.info("db query: findMessages -> found documents: #" + result.size());
                    List<Message> messages = convertDocumentsToMessages(result);
                    futureMessages.complete(messages);
                });
        return futureMessages;
    }

    private List<User> convertDocumentsToUsers(List<Document> documents) {
        return documents.stream().map(document -> new User(document.getString("_id"),
                document.getString("password"),
                document.getString("firstName"),
                document.getString("lastName"),
                document.getString("phoneNumber"))).collect(Collectors.toList());
    }

    private List<Contact> convertDocumentsToContacts(List<Document> documents) {
        return documents.stream().map(document -> new Contact(document.getString("clientUserName"),
                document.getString("contactUserName"),
                document.getString("firstName"),
                document.getString("lastName"))).collect(Collectors.toList());
    }

    private List<Message> convertDocumentsToMessages(List<Document> documents) {
        List<Message> messages = new ArrayList<>();
        for (Document document : documents) {
            messages.add(new Message(document.getString("clientUserName"),
                    document.getString("content"), document.get("contactsUserName", List.class)));
        }
        return messages;
    }
}
