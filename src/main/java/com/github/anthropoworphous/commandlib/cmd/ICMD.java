package com.github.anthropoworphous.commandlib.cmd;

import com.github.anthropoworphous.commandlib.arg.Args;
import com.github.anthropoworphous.commandlib.arg.ArgsLimiter;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICMD {
    Boolean execute(CommandSender sender, Args args);
    List<ArgsLimiter<?>> argLimiters();
    String cmdName();
    String cmdDescription();
    String cmdUsage();
    String cmdReqPerm();
    List<String> cmdAliases();
}