package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
    public boolean onCommand(CommandSender s,
                             Command cmd,
                             String label,
                             String[] strArgs) {
        ICMD c = CMDRegister.getCMD(cmd.getName()); //get command

        ArgsAnalyst analyst;
        try {
            analyst = ArgsAnalyst.of(Arrays.asList(strArgs), c.cmdRoutes()); //input, route
            return (analyst.valid()) ? c.execute(s, analyst) : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
