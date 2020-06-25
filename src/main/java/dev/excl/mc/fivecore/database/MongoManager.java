package dev.excl.mc.fivecore.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoManager {

    private static MongoDatabase database;
    private static MongoCollection<Document> playerData;

    public void connect() {
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://pluginUser:yheQ8bLQ7!ijBCQ@5kcluster-badue.gcp.mongodb.net/test?retryWrites=true&w=majority");

        database = mongoClient.getDatabase("FiveCoreDB");
        playerData = database.getCollection("players");
    }


    public MongoCollection<Document> getPlayerData() {
        return playerData;
    }

    public MongoDatabase getDatabase() { return database; }

}