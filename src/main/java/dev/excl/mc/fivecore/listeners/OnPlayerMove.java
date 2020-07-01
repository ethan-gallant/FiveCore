package dev.excl.mc.fivecore.listeners;

import dev.excl.mc.fivecore.cmds.teleport.TpACommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMove extends CoreListener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent evt) {
        if (evt.getFrom().getZ() != evt.getTo().getZ() && evt.getFrom().getX() != evt.getTo().getX()) {
            if(TpACommand.Teleporting.contains(evt.getPlayer().getUniqueId())){
                evt.getPlayer().sendMessage("Teleportation cancelled");
                TpACommand.Teleporting.remove(evt.getPlayer().getUniqueId());
            }
        }
    }
}
