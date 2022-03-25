package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.arg.analyst.implementation.Analyst;
import com.github.anthropoworphous.cmdlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CMDExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] strArgs) {
        ICMD c = CMDRegister.getCMD(cmd.getName()); //get command

        ArgsAnalyst analyst;
        try {
            analyst = new Analyst(Arrays.asList(strArgs), c.cmdRoutes()); //input, route
            return (analyst.valid()) ? c.execute(s, analyst) : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
