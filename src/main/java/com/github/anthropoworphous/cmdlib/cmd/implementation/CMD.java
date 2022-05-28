package com.github.anthropoworphous.cmdlib.cmd.implementation;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.cmd.ICMD;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class CMD implements ICMD {
    @Override
    public String cmdDescription() {
        return this.cmdName();
    }

    @Override
    public List<String> cmdUsage() {
        List<String> usages = new ArrayList<>();

        if (cmdRoutes() == null || Objects.requireNonNull(cmdRoutes()).size() == 0) {
            usages.add(" - /" + cmdName());
            return usages;
        }

        Objects.requireNonNull(cmdRoutes()).forEach(route -> {
            usages.add(" - /" + cmdName());
            route.getRoute().forEach(argType ->
                    usages.add(" " + argType.readableName())
            );
            for (ArgType<?> argType : route.getRoute()) {
                usages.add("    Def - " + argType.readableDescription());
                usages.add("    Yes - " + argType.whitelist());
                usages.add("    Nah - " + argType.blacklist());
            }
        });
        return usages;
    }

    @Override
    public String cmdReqPerm() {
        return null;
    }

    @Override
    public List<String> cmdAliases() {
        return null;
    }
}
