package com.github.anthropoworphous.commandlib;

import com.github.anthropoworphous.commandlib.adaptor.CMDExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CMDLib extends JavaPlugin {

    private static CMDExecutor executor;

    public static CMDExecutor getExecutor() { return executor; }

    @Override
    public void onEnable() {
        executor = new CMDExecutor();

        getServer().getConsoleSender().sendMessage("CommandLib loaded!");
    }
}
