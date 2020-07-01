package dev.excl.mc.fivecore.listeners;

import dev.excl.mc.fivecore.FiveCore;
import dev.excl.mc.fivecore.database.CorePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class OnPlayerDeath extends CoreListener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        new BukkitRunnable() {
            @Override
            public void run() {
                CorePlayer cp = FiveCore.getCorePlayer(victim.getUniqueId());
                cp.adjustStat("deaths", 1);
            }
        }.runTaskAsynchronously(FiveCore.getInstance());

        if (killer != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    CorePlayer cp = FiveCore.getCorePlayer(killer.getUniqueId());
                    cp.adjustStat("kills", 1);
                }
            }.runTaskAsynchronously(FiveCore.getInstance());
        }
    }
}
