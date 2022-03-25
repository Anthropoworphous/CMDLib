package com.github.anthropoworphous.cmdlib.arg.type.multitypes;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

import java.util.List;
import java.util.Optional;

public interface MultiValueArgType<T> extends ArgType<T> {
    List<ArgType<?>> separate();

    Optional<T> combine(List<Object> input);
}
