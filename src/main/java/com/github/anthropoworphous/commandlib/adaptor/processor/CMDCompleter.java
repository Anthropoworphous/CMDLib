package com.github.anthropoworphous.commandlib.adaptor.processor;

import com.github.anthropoworphous.commandlib.arg.Args;
import com.github.anthropoworphous.commandlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CMDCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command cmdName,
                                                @NotNull String label,
                                                @NotNull String[] badArgs) {
        ICMD cmd = CMDRegister.getCMD(cmdName.getName());

        return new Args(cmd.cmdLimiter(), badArgs).getAutoFill();
    }
}
