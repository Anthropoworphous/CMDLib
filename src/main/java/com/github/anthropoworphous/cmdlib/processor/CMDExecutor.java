package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.command.CMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class CMDExecutor implements CommandExecutor {
    public static CommandExecutor getExecutor() {
        if (executor == null) {
            executor = new CMDExecutor();
        }
        return executor;
    }
    private static CommandExecutor executor = null;

    @Override
    public boolean onCommand(@NotNull CommandSender s,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] strArgs) {
        CMD c = CMDRegister.getCMD(cmd.getName()); //get command

        try {
            c.getOneMatchedRoute(strArgs).ifPresentOrElse(
                    route -> {
                        if (!route.execute(s)) {
                            printUsage(c, s);
                        }
                    },
                    () -> c.usage().ifPresentOrElse(
                            usage -> printUsage(c, s),
                            () -> s.sendMessage("No command usage provided- wait how?")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            s.sendMessage("encountered error [%s] while executing the command".formatted(e.getMessage()));
        }
        return true;
    }

    private static void printUsage(CMD c, CommandSender s) {
        c.usage().ifPresent(list -> list.forEach(s::sendMessage));
    }
}
