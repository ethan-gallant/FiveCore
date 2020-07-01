package dev.excl.mc.fivecore.cmds.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dev.excl.mc.fivecore.FiveCore;
import org.bukkit.entity.Player;

@CommandAlias("tpaccept|tpac|tpyes")
public class TpAcceptCommand extends BaseCommand {
    static final Long noMoveTime = 5 * 20L;

    @Default
    @CommandCompletion("@players")
    @Syntax("[player]")
    @Description("Accept a teleport from another player")
    @CommandPermission("fivecore.teleport.accept")
    public static void onTPAccept(Player sender) {
        if (TpACommand.PendingTeleports.containsKey(sender.getUniqueId())){
            final Player requester = FiveCore.getInstance().getServer().getPlayer(TpACommand.PendingTeleports.get(sender.getUniqueId()));
            if(requester == null) {
                sender.sendMessage("It appears that player is no longer online.");
                return;
            }
            sender.sendMessage("Request Accepted");
            requester.sendMessage("Teleporting... Do not move or teleportation will be cancelled.");

            FiveCore.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(FiveCore.getInstance(), (Runnable) () -> {
                TpACommand.Teleporting.add(requester.getUniqueId());
            }, 40L);
            TpACommand.PendingTeleports.remove(sender.getUniqueId());

            FiveCore.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(FiveCore.getInstance(), (Runnable) () -> {
                if(TpACommand.Teleporting.contains(requester.getUniqueId())){
                    TpACommand.Teleporting.remove(requester.getUniqueId());
                    requester.teleportAsync(sender.getLocation());
                    requester.sendMessage("Teleported to player " + sender.getDisplayName());
                    sender.sendMessage("Player " + requester.getDisplayName() + " teleported to you.");
                }
            }, noMoveTime);
            return;
        };
        sender.sendMessage("It appears that you don't have any pending teleport requests.");
    }
}
