package dev.excl.mc.fivecore.cmds.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("tp|teleport")
public class TpCommand extends BaseCommand {
    @Default
    @CommandCompletion("@players @players")
    @Syntax("[player]")
    @Description("Teleport yourself to another player")
    @CommandPermission("fivecore.teleport.self")
    public static void onTeleport(Player sender, OnlinePlayer p1, @Optional OnlinePlayer p2) {
        if(p2 != null){
            if(p1.getPlayer().getUniqueId() == p2.getPlayer().getUniqueId()){
                sender.sendMessage("You cant teleport a player to themselves.");
                return;
            }
            sender.sendMessage("Teleporting player " + p1.getPlayer().getDisplayName() + " to player " + p2.getPlayer().getDisplayName());
            p1.getPlayer().teleportAsync(p2.getPlayer().getLocation());
            return;
        }
        if(sender.getUniqueId() == p1.getPlayer().getUniqueId()) {
            sender.sendMessage("You cant teleport to yourself.");
            return;
        }
        sender.teleportAsync(p1.getPlayer().getLocation());
        sender.sendMessage("Teleported to player " + p1.getPlayer().getDisplayName());
    }

}
