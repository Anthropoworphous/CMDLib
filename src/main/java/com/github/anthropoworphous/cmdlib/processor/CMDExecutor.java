package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


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
        ICMD c = CMDRegister.getCMD(cmd.getName()); //get command

        try {
            ArgsAnalyst analyst = ArgsAnalyst.of(Arrays.asList(strArgs), c.cmdRoutes()); //input, route
            if (!analyst.valid() || !c.execute(s, analyst)) {
                c.cmdUsage().forEach(s::sendMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
