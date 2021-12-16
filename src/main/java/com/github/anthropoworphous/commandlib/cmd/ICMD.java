package com.github.anthropoworphous.commandlib.cmd;

import com.github.anthropoworphous.commandlib.adaptor.CMDLimiter;
import com.github.anthropoworphous.commandlib.arg.Args;
import com.github.anthropoworphous.commandlib.adaptor.CMDSupplier;
import main.structure.Connected;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public interface ICMD {
    /**
     * This method is called when someone that have the required permission (if set)
     * and enter argument according to the rules definded by argsLimiters
     * @param sender The command sender
     * @param args the argument
     * @return If this method return false, the usage of this command will be send to the command sender.
     * However, that doesn't mean the action in this method is and will be cancelled.
     */
    Boolean execute(CommandSender sender, Args args);

    /**
     * Limit and shape the argmument
     * @return The limiters to use
     */
    @Nullable Collection<Connected<CMDLimiter<?>>> cmdLimiter();
    @Nullable Collection<Connected<CMDSupplier>> cmdConnector();
    @NotNull String cmdName();
    String cmdDescription();
    String cmdUsage();
    String cmdReqPerm();
    @Nullable List<String> cmdAliases();
}