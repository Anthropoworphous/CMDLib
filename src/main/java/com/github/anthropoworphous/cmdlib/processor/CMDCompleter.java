package com.github.anthropoworphous.cmdlib.processor;

import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class CMDCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender,
                                      Command cmdName,
                                      String label,
                                      String[] badArgs) {
        ICMD cmd = CMDRegister.getCMD(cmdName.getName());

        return ArgsAnalyst.of(Arrays.asList(badArgs), cmd.cmdRoutes()).getAutoFill();
    }
}
