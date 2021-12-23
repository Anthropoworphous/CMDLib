package com.github.anthropoworphous.commandlib;

import com.github.anthropoworphous.commandlib.adaptor.processor.CMDExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CMDLib extends JavaPlugin {

    private static CMDExecutor executor;
    public static CMDExecutor getExecutor() { return executor; }

    public static void log(String str) {
        Bukkit.getLogger().info("[CMDLib]: " + str);
    }

    private static boolean logDetails = false;
    public static boolean logDetails() { return logDetails; }

    @Override
    public void onEnable() {
        executor = new CMDExecutor();

        getServer().getConsoleSender().sendMessage("CommandLib loaded!");
    }

    public static void setLogDetails(boolean logDetails) { CMDLib.logDetails = logDetails; }
}
