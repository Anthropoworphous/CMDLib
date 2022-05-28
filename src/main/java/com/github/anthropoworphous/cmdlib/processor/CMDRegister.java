package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.cmd.ICMD;
import com.github.anthropoworphous.cmdlib.cmd.annotation.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings("unused")
public class CMDRegister {
    private static final Map<String, ICMD> registeredCMD = new HashMap<>();

    public static void registerCMD(JavaPlugin plugin, String packagePath) {
        log("Registering by package: searching package " + packagePath);

        Reflections reflections = new Reflections(packagePath);
        Set<Class<?>> cmdClasses = reflections.getTypesAnnotatedWith(Command.class);
        cmdClasses.forEach(c -> {
            log("-\tFound class: " + c.toString());

            Object inst = null;
            try {
                inst = c.getConstructors()[0].newInstance();
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (inst instanceof ICMD) {
                log("-\t" + c + " is a command, registering...");

                registerCMD((ICMD) inst, plugin);
            }
        });
    }
    public static void registerCMD(ICMD unregisteredICMD, JavaPlugin plugin) {
        PluginCommand cmd = createCommand(unregisteredICMD.cmdName(), plugin);
        cmd.setDescription(unregisteredICMD.cmdDescription());
        cmd.setUsage("You shouldn't see this");
        if (unregisteredICMD.cmdAliases() != null) {
            cmd.setAliases(Objects.requireNonNull(unregisteredICMD.cmdAliases()));
        }
        if (unregisteredICMD.cmdReqPerm() != null) { cmd.setPermission(unregisteredICMD.cmdReqPerm()); }
        registeredCMD.put(unregisteredICMD.cmdName(), unregisteredICMD);
        Objects.requireNonNull(getCommandMap()).register(plugin.getName(), cmd);
        Objects.requireNonNull(plugin.getCommand(cmd.getName())).setExecutor(CMDExecutor.getExecutor());
        Objects.requireNonNull(plugin.getCommand(cmd.getName())).setTabCompleter(new CMDCompleter());

        log("[CMDLib]: Command " + unregisteredICMD.cmdName() + " registered");
    }
    public static ICMD getCMD(String name) { return registeredCMD.get(name); }

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
        return null;
    }

    private static void log(String str) {
        Bukkit.getLogger().info("[CMDLib]: " + str);
    }
}