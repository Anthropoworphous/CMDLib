package com.github.anthropoworphous.cmdlib.cmd.implementation;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.cmd.ICMD;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public abstract class CMD implements ICMD {
    @Override
    public String cmdDescription() {
        return this.cmdName();
    }

    @Override
    public String cmdUsage() {
        StringBuilder sb = new StringBuilder();
        if (cmdRoutes() == null || Objects.requireNonNull(cmdRoutes()).size() == 0) {
            return (sb.append(" - /").append(cmdName())).toString();
        } else {
            Objects.requireNonNull(cmdRoutes()).forEach(route -> {
                sb.append(" - /").append(cmdName());
                route.getRoute().forEach(argType ->
                        sb.append(" ").append(argType.readableName())
                );
                for (ArgType<?> argType : route.getRoute()) {
                    sb.append(" \tDef - ").append(argType.readableDescription())
                            .append("  \tYes - ").append(argType.whitelist())
                            .append("  \tNah - ").append(argType.blacklist())
                            .append("  \tConsider - ");
                }
                sb.append("\n");
            });
            return sb.toString();
        }
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
