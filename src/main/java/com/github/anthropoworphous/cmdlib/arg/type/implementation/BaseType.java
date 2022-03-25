package com.github.anthropoworphous.cmdlib.arg.type.implementation;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class BaseType<T> implements ArgType<T> {
    public BaseType(String readableName, String readableDescription) {
        this.readableName = readableName;
        this.readableDescription = readableDescription;
    }

    private final String readableName;
    private final String readableDescription;
    private ArgType<?> dividedFrom = null;




    @Override
    public @NotNull String readableName() {
        return readableName;
    }
    @Override
    public @NotNull String readableDescription() {
        return readableDescription;
    }

    @Override
    public void dividedFrom(ArgType<?> source) {
        dividedFrom = source;
    }
    @Override
    public @NotNull Optional<ArgType<?>> dividedFrom() {
        return Optional.ofNullable(dividedFrom);
    }
}