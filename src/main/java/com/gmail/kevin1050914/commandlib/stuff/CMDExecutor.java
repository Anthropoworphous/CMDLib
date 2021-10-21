package com.gmail.kevin1050914.commandlib.stuff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CMDExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return defaultExecuter(sender, command, args);
    }

    public static boolean defaultExecuter(
            CommandSender s,
            Command cmd,
            String[] badArgs) {

        Args args = new Args(badArgs);

        return CMDRegister.getCMD(cmd.getName()).execute(s, args);
    }
}
