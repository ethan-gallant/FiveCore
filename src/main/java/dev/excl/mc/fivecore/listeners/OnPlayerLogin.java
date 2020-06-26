package dev.excl.mc.fivecore.listeners;

import dev.excl.mc.fivecore.FiveCore;
import dev.excl.mc.fivecore.database.CorePlayer;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.SimpleDateFormat;

public class OnPlayerLogin extends CoreListener {
    @EventHandler
    public void onJoin(PlayerLoginEvent event) {
        CorePlayer cp = new CorePlayer(event.getPlayer());
        cp.logIP(event.getAddress().getHostAddress());
        /*CorePlayer.Ban ban = cp.getBan();
        if(ban != null){
            if(ban.expiresAt == null){
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED,
                        ChatColor.RED + "You are banned from the network. \n" +
                                ChatColor.WHITE + "You are banned permanently. \n" +
                                ChatColor.GREEN + "Reason: " + ban.reason
                );
            } else {
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED,
                        ChatColor.RED + "You are banned from the network. \n" +
                                ChatColor.WHITE + " You are banned until " +  dt.format(ban.expiresAt) + "\n" +
                                ChatColor.GREEN + "Reason: " + ban.reason
                );
            }

        }*/
        FiveCore.putCorePlayer(cp);
    }
}
