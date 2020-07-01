package dev.excl.mc.fivecore.database;

import dev.excl.mc.fivecore.FiveCore;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;


public class CorePlayer {

    private final Player bukkitPlayer;
    private final MongoManager mongoManager;

    public CorePlayer(Player p) {
        this.bukkitPlayer = p;
        this.mongoManager = FiveCore.getMongoManager();
    }



    public UUID getUUID() {
        return bukkitPlayer.getUniqueId();
    }

    public int getStat(String statName) {
        Document playerObj =  mongoManager.getPlayerData().find(Filters.eq("uuid", this.bukkitPlayer.getUniqueId().toString())).first();
        if (playerObj == null || playerObj.isEmpty()) {
            System.out.println("Player was not found in db");
            return 0;
        }
        Document stats = (Document) playerObj.get("stats");
        if(stats == null) {
            return 0;
        }
        try {
            return stats.getInteger(statName);
        } catch (ClassCastException e)
        {
            return 0;
        }
    }

    public void adjustStat(String statName, int adjustValue) {
        String uuid = this.bukkitPlayer.getUniqueId().toString();
        new BukkitRunnable() {
            @Override
            public void run() {
                mongoManager.getPlayerData().findOneAndUpdate(Filters.eq("uuid", uuid),
                        new Document("$inc",
                                new Document("stats." + statName, adjustValue)
                        )
                );
            }
        }.runTaskAsynchronously(FiveCore.getInstance());
    }
}
