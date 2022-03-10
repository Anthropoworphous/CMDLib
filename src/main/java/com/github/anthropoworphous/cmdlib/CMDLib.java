package com.github.anthropoworphous.cmdlib;

import com.github.anthropoworphous.cmdlib.processor.CMDExecutor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class CMDLib extends JavaPlugin {

    private static CMDExecutor executor;
    public static CMDExecutor getExecutor() { return executor; }

    public static void log(String str) {
        Bukkit.getLogger().info("[CMDLib]: " + str);
    }

    @Override
    public void onEnable() {
        executor = new CMDExecutor();

        getServer().getConsoleSender().sendMessage("CommandLib loaded!");
    }
}
