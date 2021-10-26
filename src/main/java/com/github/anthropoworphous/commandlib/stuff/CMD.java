package com.github.anthropoworphous.commandlib.stuff;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CMD {
    Boolean execute(CommandSender sender, Args args);
    String cmdName();
    String cmdDescription();
    String cmdUsage();
    String cmdReqPerm();
    List<String> cmdAliases();
}

/*basic format
@Override public String CMDReqPerm() { return null; }
@Override public String CMDName() { return "name"; }
@Override public String CMDDescription() { return "desc"; }
@Override public String CMDUsage() { return "/cmd"; }
@Override public List<String> CMDAliases() { return null; }
@Override public Boolean execute(CommandSender sender, Args args) { return null; }
@Override public Boolean execute(User user, Args args) { return null; }
*/
