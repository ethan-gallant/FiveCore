package dev.excl.mc.fivecore;

import dev.excl.mc.fivecore.database.MongoManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class FiveCore extends JavaPlugin {

    public MongoManager mongoManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instanceClasses();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void instanceClasses() {

        mongoManager = new MongoManager();

    }
}
