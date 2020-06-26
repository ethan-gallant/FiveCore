package dev.excl.mc.fivecore.database;

import dev.excl.mc.fivecore.FiveCore;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import org.bukkit.entity.Player;

import java.util.*;

public class CorePlayer {


    public static class Ban {
        public String message;
        public Date expiresAt;
        public Date createdAt;
        public String type;
        public String ip;

        Punishment(String message, Date expiresAt, Date createdAt, String type , String ip) {
            this.message = message;
            this.expiresAt = expiresAt;
            this.createdAt = createdAt;
            this.type = type;
        }

    }

    private final Player bukkitPlayer;
    private final MongoManager mongoManager;
    private Punishment[] punishments;
    public boolean isTeleporting = false;

    public CorePlayer(Player p) {
        this.bukkitPlayer = p;
        this.mongoManager = FiveCore.getMongoManager();
        this.createIfNotExists();
    }

    private void createIfNotExists() {
        Document playerDocument = mongoManager.getPlayerData().find(Filters.eq("uuid", this.bukkitPlayer.getUniqueId().toString())).first();

        if (playerDocument == null) {
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


    public Punishment isBanned() {
        return null;
    }

    public void logIP(String ip) {
        mongoManager.getPlayerData().updateOne(Filters.eq("uuid", this.bukkitPlayer.getUniqueId().toString()),
                new Document("$addToSet",
                        new Document("ips", ip)
                )
        );
    }


}
