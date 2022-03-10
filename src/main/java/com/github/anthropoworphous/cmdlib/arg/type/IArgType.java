package com.github.anthropoworphous.cmdlib.arg.type;

import org.jetbrains.annotations.NotNull;

public interface IArgType {
    @NotNull String readableName();
    @NotNull String readableDescription();
}
