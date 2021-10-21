package com.gmail.kevin1050914.commandlib.stuff;

import com.gmail.kevin1050914.commandlib.CMDLib;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CMDRegister {
    private static final Map<String, CMD> REGISTERED_CMDS = new HashMap<>();

    public static void registerCMD(CMD unregisteredCMD) {
        PluginCommand cmd = Objects.requireNonNull(createCommand(unregisteredCMD.cmdName()));
        cmd.setDescription(unregisteredCMD.cmdDescription());
        cmd.setUsage(unregisteredCMD.cmdUsage());
        if (unregisteredCMD.cmdAliases() != null) { cmd.setAliases(unregisteredCMD.cmdAliases()); }
        if (unregisteredCMD.cmdReqPerm() != null) { cmd.setPermission(unregisteredCMD.cmdReqPerm()); }
        REGISTERED_CMDS.put(unregisteredCMD.cmdName(), unregisteredCMD);
        Objects.requireNonNull(getCommandMap()).register(CMDLib.getPlugin().getName(), cmd);
        Objects.requireNonNull(CMDLib.getPlugin().getCommand(cmd.getName())).setExecutor(CMDLib.getExecutor());
    }

    public static CMD getCMD(String name) { return REGISTERED_CMDS.get(name); }



    private static PluginCommand createCommand(String name) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, CMDLib.getPlugin());
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