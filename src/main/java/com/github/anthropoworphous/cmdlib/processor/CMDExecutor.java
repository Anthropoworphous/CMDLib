package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.CMDLib;
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

        if (CMDLib.logDetails()) {
            CMDLib.log("Command called: /" + cmd.getName()
                    + ((badArgs.length == 0) ?
                    " without arguments" :
                    "with argument" + ((badArgs.length == 1) ?
                            " " :
                            "s "))
                    + String.join(", ", badArgs));
        }

        ArgsGroup args;

        try {
            args = new Args(c.cmdArgType(), badArgs, true);
            return (args.getAnalyst().validate()) ? c.execute(s, args.getAnalyst()) : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
