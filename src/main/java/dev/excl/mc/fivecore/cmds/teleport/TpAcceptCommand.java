package dev.excl.mc.fivecore.cmds.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dev.excl.mc.fivecore.FiveCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@CommandAlias("tpaccept|tpac|tpyes")
public class TpAcceptCommand extends BaseCommand {
    static final Long noMoveTime = 5 * 20L;
    @Default
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Teleports to another Player")
    public static void onTPAccept(Player sender) {
        if (TpaCommand.PendingTeleports.containsKey(sender.getUniqueId())){
            final Player requester = FiveCore.getInstance().getServer().getPlayer(TpaCommand.PendingTeleports.get(sender.getUniqueId()));
            if(requester == null) {
                sender.sendMessage("It appears that player is no longer online.");
                return;
            }
            sender.sendMessage("Request Accepted");
            requester.sendMessage("Teleporting... Do not move or teleportation will be cancelled.");

            FiveCore.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(FiveCore.getInstance(), (Runnable) () -> {
                TpaCommand.Teleporting.add(requester.getUniqueId());
            }, 20L);
            TpaCommand.Teleporting.add(requester.getUniqueId());
            TpaCommand.PendingTeleports.remove(sender.getUniqueId());

            FiveCore.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(FiveCore.getInstance(), (Runnable) () -> {
                if(TpaCommand.Teleporting.contains(requester.getUniqueId())){
                    requester.teleportAsync(sender.getLocation());
                    requester.sendMessage("Teleported to player " + sender.getDisplayName());
                    sender.sendMessage("Player " + requester.getDisplayName() + " teleported to you.");
                }
                TpaCommand.Teleporting.remove(requester.getUniqueId());
            }, noMoveTime);
            return;
        };
        sender.sendMessage("It appears that you don't have any pending teleport requests.");
    }
}
