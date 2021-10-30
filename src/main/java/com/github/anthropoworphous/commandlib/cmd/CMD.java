package com.github.anthropoworphous.commandlib.cmd;

import com.github.anthropoworphous.commandlib.arg.ArgsLimiter;

import java.util.List;

public abstract class CMD implements ICMD {
    @Override
    public List<ArgsLimiter<?>> argLimiters() {
        return null;
    }

    @Override
    public String cmdDescription() {
        return this.cmdName();
    }

    @Override
    public String cmdUsage() {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(this.cmdName());
        if (argLimiters() != null) {
            argLimiters().forEach(param -> {
                if (param != null) {
                    sb.append(" ").append(param.getExpectedType().getHumanReadableName());
                } else {
                    sb.append(" ").append("<Unknown>");
                }
            });
            int index = 0;
            for (ArgsLimiter<?> argLimitor : argLimiters()) {
                if (argLimitor == null) { continue; }
                index++;
                sb.append("\n  - §aArg ").append(index)
                        .append("§r: §d")
                        .append(argLimitor.getExpectedType().getHumanReadableName())
                        .append("§r || §9")
                        .append(argLimitor.getExpectedType().getgetHumanReadableDescription());
                if (argLimitor.getLimit() != null) {
                    sb.append("\n      - §cLimited!§r ")
                            .append(argLimitor.isWhiteList() ? "WhiteList" : "§7BlackList§r");
                    for (Object arg : argLimitor.getLimit()) {
                        sb.append("\n          - ").append(arg);
                    }
                }
            }
        }
        return sb.toString();
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
