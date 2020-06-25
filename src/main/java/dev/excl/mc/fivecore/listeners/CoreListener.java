package dev.excl.mc.fivecore.listeners;

import dev.excl.mc.fivecore.FiveCore;
import org.bukkit.event.Listener;

public abstract class CoreListener implements Listener {

    public void initEvent() {
        FiveCore.getInstance().getServer().getPluginManager().registerEvents(this, FiveCore.getInstance());
    }
}
