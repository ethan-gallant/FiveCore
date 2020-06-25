package dev.excl.mc.fivecore.database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import dev.excl.mc.fivecore.FiveCore;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;


public class CorePlayer {

    private class Ban {
        String reason;
        String endsAt;
        String createdAt;
        Ban(String reason, String endsAt, String createdAt){
            this.reason = reason;
            this.endsAt = endsAt;
            this.createdAt = createdAt;
        }
    }
    private final Player bukkitPlayer;
    private final MongoManager mongoManager;

    public boolean isTeleporting = false;

    public CorePlayer(Player p){
        this.bukkitPlayer = p;
        this.mongoManager = FiveCore.getMongoManager();
        this.createIfNotExists();
    }

    private void createIfNotExists(){
        Document playerDocument = mongoManager.getPlayerData().find(Filters.eq("uuid",this.bukkitPlayer.getUniqueId().toString())).first();

        if(playerDocument == null){
            MongoCollection<Document> pd = mongoManager.getPlayerData();
            Document player = new Document("uuid", bukkitPlayer.getUniqueId())
                    .append("coins", "0")
                    .append("ips", Collections.singletonList(bukkitPlayer.getAddress().getHostName()))
                    .append("last_login", new Date())
                    .append("first_joined", new Date())
                    .append("punishments", Collections.emptyList());
            mongoManager.getPlayerData().insertOne(player);
            return;
        }

        mongoManager.getPlayerData().updateOne(Filters.eq("uuid",this.bukkitPlayer.getUniqueId().toString()),
                new Document("$set",
                        new Document("last_login", new Date())
                )
        );

        mongoManager.getPlayerData().updateOne(Filters.eq("uuid",this.bukkitPlayer.getUniqueId().toString()),
                new Document("$push",
                        new Document("ips", bukkitPlayer.getAddress().getHostName())
                )
        );

    }

    public UUID getUUID() {
        return bukkitPlayer.getUniqueId();
    }


    public Ban getBan() {
        Document banDocument = mongoManager.getPlayerData().find(Filters.gte("punishments.expires_at", new Date())).first();
        if(banDocument == null)
            return null;
        return new Ban((String) banDocument.get("message"),(String) banDocument.get("expires_at"), (String) banDocument.get("created_at"));
    }
}
