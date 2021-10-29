package com.github.anthropoworphous.commandlib.adaptor;

import com.github.anthropoworphous.commandlib.CMDLib;
import com.github.anthropoworphous.commandlib.cmd.ICMD;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CMDRegister {
    private static final Map<String, ICMD> REGISTERED_CMDS = new HashMap<>();

    public static void registerCMD(ICMD unregisteredICMD, JavaPlugin plugin) {
        PluginCommand cmd = Objects.requireNonNull(createCommand(unregisteredICMD.cmdName(), plugin));
        cmd.setDescription(unregisteredICMD.cmdDescription());
        cmd.setUsage(unregisteredICMD.cmdUsage());
        if (unregisteredICMD.cmdAliases() != null) { cmd.setAliases(unregisteredICMD.cmdAliases()); }
        if (unregisteredICMD.cmdReqPerm() != null) { cmd.setPermission(unregisteredICMD.cmdReqPerm()); }
        REGISTERED_CMDS.put(unregisteredICMD.cmdName(), unregisteredICMD);
        Objects.requireNonNull(getCommandMap()).register(plugin.getName(), cmd);
        Objects.requireNonNull(plugin.getCommand(cmd.getName())).setExecutor(CMDLib.getExecutor());
    }

    public static ICMD getCMD(String name) { return REGISTERED_CMDS.get(name); }



    private static PluginCommand createCommand(String name, JavaPlugin plugin) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (NoSuchMethodException | InvocationTargetException |
                InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return command;
    }

    private static CommandMap getCommandMap() {
        try {
            if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
                Field f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);

                return (CommandMap) f.get(Bukkit.getPluginManager());
            }
        } catch (NoSuchFieldException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}