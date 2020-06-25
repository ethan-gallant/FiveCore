package dev.excl.mc.fivecore.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;

public class MongoManager {

    private MongoDatabase database;
    private static MongoCollection playerData;

    public void connect() {
        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://pluginUser:yheQ8bLQ7!ijBCQ@5kcluster-badue.gcp.mongodb.net/test?retryWrites=true&w=majority");

        setDatabase(mongoClient.getDatabase("FiveCoreDB"));
    }

    public void setPlayerDataDocument( String uuid, String identifier, Object value) {
        Document document = new Document("uuid", uuid);
        Bson newValue = new Document(identifier, value);
        Bson updateOperation = new Document("&set", newValue);
        playerData.updateOne(document, updateOperation);
    }

    public MongoCollection getPlayerData() {
        return playerData;
    }


    public void setPlayerData(MongoCollection playerData) {
        MongoManager.playerData = playerData;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

}