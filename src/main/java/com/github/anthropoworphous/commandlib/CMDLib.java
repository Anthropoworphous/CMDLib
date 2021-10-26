package com.github.anthropoworphous.commandlib;

import com.github.anthropoworphous.commandlib.stuff.CMDExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CMDLib extends JavaPlugin {

    private static JavaPlugin plugin;
    private static CMDExecutor executor;

    public static JavaPlugin getPlugin() { return plugin; }
    public static void setPlugin(final JavaPlugin plugin) { CMDLib.plugin = plugin; }
    public static CMDExecutor getExecutor() { return executor; }

    @Override
    public void onEnable() {
        executor = new CMDExecutor();
        getServer().getConsoleSender().sendMessage("CommandLib loaded!");
    }
}
