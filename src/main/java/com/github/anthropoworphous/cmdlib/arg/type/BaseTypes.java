package com.github.anthropoworphous.cmdlib.arg.type;

import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public abstract class BaseTypes<S extends BaseTypes<S, T>, T> implements ArgType<S, T> {
    public BaseTypes(String readableName, String readableDescription) {
        this.readableName = readableName;
        this.readableDescription = readableDescription;
        this.parser = new BaseArgParser<>(this);
    }

    private final String readableName;
    private final String readableDescription;
    private final ArgParser<S, T> parser;

    @Override
    public @NotNull String readableName() {
        return readableName;
    }
    @Override
    public @NotNull String readableDescription() {
        return readableDescription;
    }

    //default override for BaseTypes
    @Override
    @NotNull
    public String argTypeToString(T input) { return String.valueOf(input); }

    @Override
    public @NotNull ArgParser<S, T> parser() {
        return parser;
    }
}
