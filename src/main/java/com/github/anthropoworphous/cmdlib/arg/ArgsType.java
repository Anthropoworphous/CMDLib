package com.github.anthropoworphous.cmdlib.arg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ArgsType {
    String getReadableName();
    String getReadableDescription();

    @Nullable <T> T stringToArgType(String input);
    @NotNull String argTypeToString(Object input);
    @NotNull Class<?> returnType();
}
