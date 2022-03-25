package com.github.anthropoworphous.cmdlib.arg.type;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface ArgType<T> {
    boolean isMultiValue();

    @NotNull String readableName();
    @NotNull String readableDescription();

    @NotNull List<T> whitelist();
    @NotNull List<T> blacklist();

    @NotNull Optional<ArgType<?>> dividedFrom();
    void dividedFrom(ArgType<?> source);
}
