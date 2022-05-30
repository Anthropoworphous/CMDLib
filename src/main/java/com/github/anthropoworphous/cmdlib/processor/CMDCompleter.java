package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.command.CMD;
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
                                                @NotNull String[] args) {
        CMD cmd = CMDRegister.getCMD(cmdName.getName());

        return cmd.getAutoComplete(args)
                .flatMap(var -> var.getAutoComplete(args[args.length-1]))
                .toList();
    }
}
