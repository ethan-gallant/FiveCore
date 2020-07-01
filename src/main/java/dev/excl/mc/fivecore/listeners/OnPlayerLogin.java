package dev.excl.mc.fivecore.listeners;

import dev.excl.mc.fivecore.FiveCore;
import dev.excl.mc.fivecore.database.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;

public class OnPlayerLogin extends CoreListener {
    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        CorePlayer cp = new CorePlayer(event.getPlayer());
        new BukkitRunnable() {
            @Override
            public void run() {
                cp.adjustStat("logins",1);
            }
        }.runTaskAsynchronously(FiveCore.getInstance());
        FiveCore.putCorePlayer(cp);
    }
}
