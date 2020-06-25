package dev.excl.mc.fivecore.listeners;

import dev.excl.mc.fivecore.FiveCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import dev.excl.mc.fivecore.database.CorePlayer;
import org.bukkit.event.player.PlayerLoginEvent;

public class OnPlayerLogin extends CoreListener {
    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        CorePlayer cp = new CorePlayer(event.getPlayer());
        if(cp.getBan() != null){
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "You are banned");
        }
        FiveCore.putCorePlayer(cp);
    }
}
