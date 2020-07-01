package dev.excl.mc.fivecore.api;

import dev.excl.mc.fivecore.FiveCore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    private FiveCore plugin = FiveCore.getInstance();

    private Economy provider;

    public void hook() {
        provider = plugin.getEconomyImplementer();
        Bukkit.getServicesManager().register(Economy.class, this.provider, this.plugin, ServicePriority.Normal);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI hooked info " + ChatColor.AQUA + FiveCore.getInstance().getName());
        System.out.println("Hooked ito vault");
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, provider);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "VaultAPI unhooked info " + ChatColor.AQUA + FiveCore.getInstance().getName());

    }
}
