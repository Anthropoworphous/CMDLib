package com.github.anthropoworphous.cmdlib.arg.parser;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

import java.util.List;

public interface IArgParser<T> extends ModifiableParser<T> {
    boolean validation(String input);
    T parse(String input);
    ArgType<T> getArgType();

    List<String> getAutoFill();
    List<T> getWhitelist();
    List<T> getBlacklist();
}
