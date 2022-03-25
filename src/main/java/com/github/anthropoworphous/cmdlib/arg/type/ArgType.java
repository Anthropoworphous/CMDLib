package com.github.anthropoworphous.cmdlib.arg.type;


import java.util.List;
import java.util.Optional;

public interface ArgType<T> {
    boolean isMultiValue();

    String readableName();

    String readableDescription();

    List<T> whitelist();

    List<T> blacklist();

    Optional<ArgType<?>> dividedFrom();

    void dividedFrom(ArgType<?> source);
}
