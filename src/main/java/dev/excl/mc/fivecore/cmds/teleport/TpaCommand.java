package dev.excl.mc.fivecore.cmds.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dev.excl.mc.fivecore.FiveCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@CommandAlias("tpa|teleportask|tpask")
public class TpaCommand extends BaseCommand {
    public static ConcurrentHashMap<UUID, UUID> PendingTeleports = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<UUID, Long> TeleportCooldowns = new ConcurrentHashMap<>();
    public static List<UUID> Teleporting = new ArrayList<>();

    private static final long timeout = 30 * 20L;
    private static final Long cooldown = 5 * 20L;
    @Default
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Teleports to another Player")

    public static void onTPA(Player sender, OnlinePlayer targetOP) {
        Player target = targetOP.getPlayer();
        if (sender.getUniqueId() == target.getUniqueId()) {
            sender.sendMessage(ChatColor.RED + "You can not send a teleport yourself, ya goof ;)");
            return;
        }
        if(!sender.hasPermission("fivecore.tp.bypasscooldown")) {
            if(TeleportCooldowns.containsKey(sender.getUniqueId())) {
                final long diff = (System.currentTimeMillis() - TeleportCooldowns.get(sender.getUniqueId())) / 1000L;
                if (diff < cooldown) {
                    sender.sendMessage(ChatColor.AQUA + "You must wait at least " + cooldown + " seconds in between teleport requests!");
                    return;
                }
                TeleportCooldowns.remove(sender.getUniqueId());
            }
        }
        sendTPARequest(sender,target);

        FiveCore.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(FiveCore.getInstance(), (Runnable) () -> {
            timeoutRequest(target);
        }, timeout);

        TeleportCooldowns.put(sender.getUniqueId(), System.currentTimeMillis());
    }


    static void timeoutRequest(Player target) {
        if (PendingTeleports.containsKey(target.getUniqueId())) {
            final Player requester = FiveCore.getInstance().getServer().getPlayer(PendingTeleports.get(target.getUniqueId()));
            if(requester != null) {
                requester.sendMessage("Your teleport request to " + target.getDisplayName() + " timed out.");
            }
            PendingTeleports.remove(target.getUniqueId());
        }
    }

    static void sendTPARequest(Player sender, Player target) {
        sender.sendMessage(ChatColor.RED + "Teleport Request Sent to " + ChatColor.GREEN + target.getDisplayName());
        target.sendMessage(ChatColor.RED + "Pending Teleport Request From " + ChatColor.GREEN + sender.getDisplayName());
        target.sendMessage(ChatColor.RED + "Type " + ChatColor.GREEN + "/tpaccept" + ChatColor.RED + " to accept");
        target.sendMessage(ChatColor.RED + "Type " + ChatColor.DARK_RED + "/tpdeny" + ChatColor.RED + " to deny");
        PendingTeleports.put(target.getUniqueId(), sender.getUniqueId());
    }
}


