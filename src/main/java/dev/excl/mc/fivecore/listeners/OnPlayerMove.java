package dev.excl.mc.fivecore.listeners;

import dev.excl.mc.fivecore.FiveCore;
import dev.excl.mc.fivecore.cmds.teleport.TpaCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMove extends CoreListener {
    @EventHandler
    void onPlayerMove(PlayerMoveEvent evt) {
        TpaCommand.Teleporting.remove(evt.getPlayer().getUniqueId());

    }
}
