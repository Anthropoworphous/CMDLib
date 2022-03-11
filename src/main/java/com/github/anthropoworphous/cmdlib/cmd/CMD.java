package com.github.anthropoworphous.cmdlib.cmd;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

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
        if (cmdArgType() == null || Objects.requireNonNull(cmdArgType()).size() == 0) {
            Objects.requireNonNull(cmdArgType()).forEach(root -> {
                sb.append(" - /").append(cmdName());
                root.toList().forEach(route ->
                    {
                        route.forEach(argType ->
                                sb.append(" ").append(argType.value().orElseThrow().readableName())
                        );
                        route.forEach(argType -> {
                            ArgType<?> a = argType.value().orElseThrow();
                            sb.append(" \tDef - ").append(a.readableDescription())
                                    .append("  \tYes - ").append(a.parser().getWhiteList())
                                    .append("  \tNah - ").append(a.parser().getBlackList())
                                    .append("  \tConsider - ");
                            a.parser().getAutoFill().subList(0, Math.min(a.parser().getAutoFill().size(), 3))
                                    .forEach(autoFill -> sb.append(autoFill).append(" "));
                        });
                        sb.append("\n");
                    }
                );
            });
            return sb.toString();
        }
        return (sb.append(" - /").append(cmdName())).toString();
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
