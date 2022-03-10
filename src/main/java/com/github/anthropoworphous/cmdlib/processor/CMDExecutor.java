package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.arg.Args;
import com.github.anthropoworphous.cmdlib.arg.ArgsGroup;
import com.github.anthropoworphous.cmdlib.cmd.ICMD;
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

        Args args;
        try {
            args = new ArgsGroup(c.cmdArgType(), badArgs, true);
            return (args.getAnalyst().validate()) ? c.execute(s, args.getAnalyst()) : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
