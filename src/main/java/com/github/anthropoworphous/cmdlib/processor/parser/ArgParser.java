package com.github.anthropoworphous.cmdlib.processor.parser;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

import java.util.List;

public interface ArgParser<T> extends ModifiableParser<T> {
    ArgType<T> getArgType();
    T parse(String input);
    boolean validation(String input);

    List<String> getAutoFill();
    List<T> getWhiteList();
    List<T> getBlackList();
}
