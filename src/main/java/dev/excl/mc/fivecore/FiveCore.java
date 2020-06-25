package dev.excl.mc.fivecore;
import java.util.logging.Logger;

import co.aikar.commands.PaperCommandManager;
import dev.excl.mc.fivecore.cmds.teleport.TpaCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class FiveCore extends JavaPlugin {
    private static FiveCore Instance;
    Logger Logger = Bukkit.getLogger();
    ConsoleCommandSender clogger = this.getServer().getConsoleSender(); // must type .sendMessage(color + "Message") after it
    @Override
    public void onEnable() {
        clogger.sendMessage(ChatColor.RED + "---------------------------------------");
        clogger.sendMessage(ChatColor.GREEN + "FiveCore Has Been Enabled");
        clogger.sendMessage(ChatColor.RED + "---------------------------------------");

        Instance = this;
        /*
        * Save Default Config File if it doesnt exist
        * */
        FileConfiguration config = this.getConfig();
        this.saveDefaultConfig();
        System.out.println(config.getString("test"));


        PaperCommandManager manager = new PaperCommandManager(Instance);
        /*
        * Command Registration
        * */
        manager.registerCommand(new TpaCommand());
    }

    @Override
    public void onDisable() {
        clogger.sendMessage(ChatColor.GREEN + "---------------------------------------");
        clogger.sendMessage(ChatColor.RED + "FiveCore Has Been Disabled");
        clogger.sendMessage(ChatColor.GREEN + "---------------------------------------");
    }
    public static FiveCore getInstance() {
        return Instance;
    }
}
