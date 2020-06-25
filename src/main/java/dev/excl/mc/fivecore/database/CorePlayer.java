package dev.excl.mc.fivecore.database;

import com.mongodb.client.MongoCollection;
import dev.excl.mc.fivecore.FiveCore;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CorePlayer {


    public static class Ban {
        public String reason;
        public Date expiresAt;
        public Date createdAt;

        Ban(String reason, Date expiresAt, Date createdAt) {
            this.reason = reason;
            this.expiresAt = expiresAt;
            this.createdAt = createdAt;
        }

    }

    private final Player bukkitPlayer;
    private final MongoManager mongoManager;

    public boolean isTeleporting = false;

    public CorePlayer(Player p) {
        this.bukkitPlayer = p;
        this.mongoManager = FiveCore.getMongoManager();
        this.createIfNotExists();
    }

    private void createIfNotExists() {
        Document playerDocument = mongoManager.getPlayerData().find(Filters.eq("uuid", this.bukkitPlayer.getUniqueId().toString())).first();

        if (playerDocument == null) {
            MongoCollection<Document> pd = mongoManager.getPlayerData();
            Document player = new Document("uuid", bukkitPlayer.getUniqueId().toString())
                    .append("coins", "0")
                    .append("ips", Collections.singletonList(bukkitPlayer.getAddress().toString()))
                    .append("last_login", new Date())
                    .append("first_joined", new Date())
                    .append("punishments", Collections.emptyList());
            mongoManager.getPlayerData().insertOne(player);
            return;
        }

        mongoManager.getPlayerData().updateOne(Filters.eq("uuid", this.bukkitPlayer.getUniqueId().toString()),
                new Document("$set",
                        new Document("last_login", new Date())
                )
        );


    }

    public UUID getUUID() {
        return bukkitPlayer.getUniqueId();
    }


    public Ban getBan() {
        Document bansDocument = mongoManager.getPlayerData().find(
                Filters.elemMatch("punishments", Document.parse("{expires_at: { $gt: ISODate() }, pardoned_by: {$exists: false}}"))
        ).first();

        if (bansDocument == null) {
            return null;
        }

        List<Document> banDocs = (List<Document>) bansDocument.get("punishments");
        Document banDocument = banDocs.get(0);
        System.out.println("We got this data: " + banDocument.toString());
        return new Ban(banDocument.getString("message"), banDocument.getDate("expires_at"), banDocument.getDate("created_at"));
    }

    public void logIP(String ip) {
        mongoManager.getPlayerData().updateOne(Filters.eq("uuid", this.bukkitPlayer.getUniqueId().toString()),
                new Document("$addToSet",
                        new Document("ips", ip)
                )
        );
    }
}
