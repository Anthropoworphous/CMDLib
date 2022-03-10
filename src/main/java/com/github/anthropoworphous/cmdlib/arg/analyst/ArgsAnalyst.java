package com.github.anthropoworphous.cmdlib.arg.analyst;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;

import java.util.List;
import java.util.Optional;

public interface ArgsAnalyst {
    boolean validate();
    int getInputSize();
    <T> T get(int index);
    List<String> getAutoFill();
    List<ArgParser<?>> getParser();

    default Optional<ArgParser<?>> getParser(int index) {
        return Optional.ofNullable(getParser().get(index));
    }
    default Optional<ArgType<?>> getArgType(int index) {
        return getParser(index).map(ArgParser::getArgType);
    }
}
