package com.github.anthropoworphous.cmdlib.adaptor.processor;

import com.github.anthropoworphous.cmdlib.CMDLib;
import com.github.anthropoworphous.cmdlib.arg.Args;
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

        Args args = new Args(badArgs, c.cmdLimiter());

        if (CMDLib.logDetails()) {
            CMDLib.log("Argument " + ((badArgs.length == 1) ? " " : "s ")
                    + "is " + ((args.isValid() ?
                    "valid" :
                    "invalid, because: " + args.invalidReason())));
        }

        return (args.isValid()) ? c.execute(s, args) : false;
    }
}
