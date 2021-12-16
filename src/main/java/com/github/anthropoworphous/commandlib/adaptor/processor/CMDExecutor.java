package com.github.anthropoworphous.commandlib.adaptor.processor;

import com.github.anthropoworphous.commandlib.arg.Args;
import com.github.anthropoworphous.commandlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CMDExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender s,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] badArgs) {
        ICMD c = CMDRegister.getCMD(cmd.getName());

        Args args = new Args(badArgs, c.cmdLimiter());

        return (args.isValid()) ? c.execute(s, args) : false;
    }
}
