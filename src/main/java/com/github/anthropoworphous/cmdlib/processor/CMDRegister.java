package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.command.CMD;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class CMDRegister {
    private static final Map<String, CMD> registeredCMD = new HashMap<>();
    public static void registerCMD(CMD unregisteredCMD, JavaPlugin plugin) {
        PluginCommand cmd = createCommand(unregisteredCMD.name(), plugin);

        cmd.setUsage("You shouldn't see this"); // is re-routed to allow multiple lines
        cmd.setDescription(unregisteredCMD.description().orElse("No info"));
        unregisteredCMD.aliases().ifPresent(cmd::setAliases);
        unregisteredCMD.permission().ifPresent(cmd::setPermission);

        registeredCMD.put(unregisteredCMD.name(), unregisteredCMD);

        getCommandMap().register(plugin.getName(), cmd);
        Optional.ofNullable(plugin.getCommand(cmd.getName()))
                .ifPresentOrElse(
                        actualCommand -> {
                            actualCommand.setExecutor(CMDExecutor.getExecutor());
                            actualCommand.setTabCompleter(new CMDCompleter());
                            log("[CMDLib]: Command %s registered".formatted(unregisteredCMD.name()));
                        },
                        () -> log("Failed to register %s".formatted(unregisteredCMD)));
    }
    public static CMD getCMD(String name) { return registeredCMD.get(name); }

    private static PluginCommand createCommand(String name, JavaPlugin plugin) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            command = c.newInstance(name, plugin);
        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Failed to get SimplePluginManager.commandMap");
    }

    private static void log(String str) {
        Bukkit.getServer().getConsoleSender().sendMessage("[CMDLib]: " + str);
    }
}