package com.github.anthropoworphous.commandlib;

import com.github.anthropoworphous.commandlib.adaptor.CMDExecutor;
import com.github.anthropoworphous.commandlib.adaptor.CMDRegister;
import org.bukkit.plugin.java.JavaPlugin;

public final class CMDLib extends JavaPlugin {

    private static CMDExecutor executor;

    public static CMDExecutor getExecutor() { return executor; }

    @Override
    public void onEnable() {
        executor = new CMDExecutor();

        CMDRegister.registerCMD(new ExampleCMD(), this);

        getServer().getConsoleSender().sendMessage("CommandLib loaded!");
    }
}
