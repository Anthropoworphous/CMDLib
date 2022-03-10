package com.github.anthropoworphous.cmdlib.processor.parser;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

import java.util.List;

public interface ArgParser<A extends ArgType<A, T>, T> extends ModifiableParser<T> {
    ArgType<A, T> getArgType();
    T parse(String input);
    boolean validation(String input);

    List<String> getAutoFill();
}
