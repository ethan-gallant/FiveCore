package dev.excl.mc.fivecore.database;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDatabase {

    private MongoCollection<Document> playerData;

    public PlayerDatabase() {
        MongoClient mongoClient = MongoClients.create("%{monogoClientURL}");
        MongoDatabase database = mongoClient.getDatabase("%player-database");
        playerData = database.getCollection("playerData");
    }


    @EventHandler
    public void join(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Document playerdoc = new Document("UUID", player.getUniqueId());
        Document found = (Document) playerData.find(playerdoc).first();
        if(found == null) {
            playerData.insertOne(playerdoc);
        }

    }








}
