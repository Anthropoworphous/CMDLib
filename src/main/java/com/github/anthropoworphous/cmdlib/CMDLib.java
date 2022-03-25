package com.github.anthropoworphous.cmdlib;

import org.bukkit.plugin.java.JavaPlugin;

public class CMDLib extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("CommandLib loaded!");
    }
}
