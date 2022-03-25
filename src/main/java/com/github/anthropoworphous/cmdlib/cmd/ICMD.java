package com.github.anthropoworphous.cmdlib.cmd;

import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.arg.route.IRoute;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICMD {
    /**
     * This method is called when someone that have the required permission (if set)
     * and enter argument according to the rules definded by argsLimiters
     *
     * @param sender  The command sender
     * @param analyst the argument analyst
     * @return If this method return false, the usage of this command will be send to the command sender.
     * However, that doesn't mean the action in this method is and will be cancelled.
     */
    Boolean execute(CommandSender sender, ArgsAnalyst analyst);

    /**
     * Limit and shape the argmument
     *
     * @return The limiters to use
     */
    List<IRoute> cmdRoutes();

    String cmdName();

    String cmdDescription();

    String cmdUsage();

    String cmdReqPerm();

    List<String> cmdAliases();
}