package dev.excl.mc.fivecore.cmds.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

@CommandAlias("tpa|teleportask|tpask")
public class TpaCommand extends BaseCommand {
    private static ConcurrentHashMap<Player, Player> PendingTeleports = new ConcurrentHashMap<>();

    @Default
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Teleports to another Player")

    public static void onTPA(Player player, OnlinePlayer target) {
        Player player2 = target.getPlayer();
        if(player.getUniqueId() == player2.getUniqueId()){
            player.sendMessage(ChatColor.RED + "You can not send a teleport yourself, ya goof ;)");
            return;
        }
        player.sendMessage(ChatColor.RED + "Teleport Request Sent to " + ChatColor.GREEN + player2.getDisplayName());
        player2.sendMessage(ChatColor.RED + "Pending Teleport Request From " + ChatColor.GREEN + player.getDisplayName());
        player2.sendMessage(ChatColor.RED + "Type " + ChatColor.GREEN +  "/tpaccept" + ChatColor.RED + " to accept");
        player2.sendMessage(ChatColor.RED + "Type " + ChatColor.DARK_RED +  "/tpdeny" + ChatColor.RED + " to deny");
        PendingTeleports.put(player2, player);
    }

    public ConcurrentHashMap getPendingTeleports() {
        return PendingTeleports;
    }
}


