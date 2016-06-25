package database;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import utils.Constants;

/**
 * Created by sia on 6/25/16.
 */
public class Util {
    public static MongoDatabase getDatabase() {
        MongoClient mongoClient = MongoClients.create();
        return mongoClient.getDatabase(Constants.DATABASE_NAME);
    }
}
