package com.github.anthropoworphous.commandlib.adaptor.processor;

import com.github.anthropoworphous.commandlib.adaptor.CMDSupplier;
import com.github.anthropoworphous.commandlib.cmd.ICMD;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CMDCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender,
                                                @NotNull Command cmd,
                                                @NotNull String label,
                                                @NotNull String[] badArgs) {
        ICMD c = CMDRegister.getCMD(cmd.getName());

        if (c.cmdConnector() == null) { return null; }

        List<Function<CMDSupplier, Boolean>> filters = new ArrayList<>();

        Arrays.stream(badArgs).forEach(arg -> filters.add(
                supplier -> supplier.getPossibleArgs().stream()
                        .anyMatch(str -> str.startsWith(arg))
        ));

        return Objects.requireNonNull(c.cmdConnector()).stream()
                .flatMap(l -> l.multiLayerFilter(filters, 0).stream())
                .flatMap(connected -> connected.getValue().getPossibleArgs().stream())
                .collect(Collectors.toList());
    }
}
