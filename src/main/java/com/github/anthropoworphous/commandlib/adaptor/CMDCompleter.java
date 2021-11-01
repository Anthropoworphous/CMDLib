package com.github.anthropoworphous.commandlib.adaptor;

import com.github.anthropoworphous.commandlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class CMDCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command cmd,
                                                @NotNull String label,
                                                @NotNull String[] badArgs) {
        ICMD c = CMDRegister.getCMD(cmd.getName());
        return c.cmdConnector().match(Arrays.asList(badArgs), 0);
    }
}
