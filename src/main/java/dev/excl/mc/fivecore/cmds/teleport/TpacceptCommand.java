package dev.excl.mc.fivecore.cmds.teleport;

import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class TpacceptCommand {
    static TpaCommand Pending = new TpaCommand();
    ConcurrentHashMap ActiveTeleports = new ConcurrentHashMap();


    @Default
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Teleports to another Player")

    public static void onTpaccept(Player player, String[] args) {
        ConcurrentHashMap PendingTeleports = Pending.getPendingTeleports();
        if(PendingTeleports.containsKey(player)){
        }
    }
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Teleports to another Player")

    public static void onTpacceptPlayer(Player player, OnlinePlayer target) {
        Player player2 = target.getPlayer();
        if(player.getUniqueId() == player2.getUniqueId()){
            player.sendMessage(ChatColor.RED + "You can not send a accept a teleport to yourself, ya goof ;)");
            return;
        }
    }

}
