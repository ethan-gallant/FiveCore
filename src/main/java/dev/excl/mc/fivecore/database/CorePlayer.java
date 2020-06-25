package dev.excl.mc.fivecore.database;

import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.UUID;


public class CorePlayer {

    private final Player p;
    private Document data;
//    private final MongoManager mongoManager;

    CorePlayer(Player p){
        this.p = p;
//        this.mongoManager = FiveCore.get
        this.createIfNotExists();
    }

    private void createIfNotExists(){

    }
}
