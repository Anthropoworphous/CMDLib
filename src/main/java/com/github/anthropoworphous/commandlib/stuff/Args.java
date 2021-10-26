package com.github.anthropoworphous.commandlib.stuff;

import java.util.List;

public class Args {
    public Args(String[] badArgs) {
        this.badArgs = List.of(badArgs);
    }

    private final List<String> badArgs;

    public String get(Integer index) {
        if (badArgs.size() > (index)) {
            return badArgs.get(index);
        } else { return null; }
    }
}
