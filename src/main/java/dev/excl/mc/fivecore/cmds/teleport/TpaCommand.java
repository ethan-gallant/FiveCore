package dev.excl.mc.fivecore.cmds.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandAlias("tpa|teleportask|tpask")
public class TpaCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Teleports to another Player")
    public static void onTPA(Player player, OnlinePlayer target) {

        if (target == null) {
            player.sendMessage(ChatColor.RED + "You must teleport to an online player");
            return;
        }
        Player player2 = target.getPlayer();
        player.sendMessage(ChatColor.RED + player2.getDisplayName());
    }

}

