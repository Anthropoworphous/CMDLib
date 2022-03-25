package com.github.anthropoworphous.cmdlib.arg.type.implementation;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

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
    public String readableName() {
        return readableName;
    }

    @Override
    public String readableDescription() {
        return readableDescription;
    }

    @Override
    public void dividedFrom(ArgType<?> source) {
        dividedFrom = source;
    }

    @Override
    public Optional<ArgType<?>> dividedFrom() {
        return Optional.ofNullable(dividedFrom);
    }
}