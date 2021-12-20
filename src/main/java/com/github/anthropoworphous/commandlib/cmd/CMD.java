package com.github.anthropoworphous.commandlib.cmd;

import java.util.List;

@SuppressWarnings("unused")
public abstract class CMD implements ICMD {
    @Override
    public String cmdDescription() {
        return this.cmdName();
    }

    @Override
    public String cmdUsage() {
        return "idk";
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
