package com.github.anthropoworphous.cmdlib.arg.type.multitypes;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface MultiValueArgType<T> extends ArgType<T> {
    @NotNull List<ArgType<?>> separate();
    @NotNull Optional<T> combine(List<Object> input);
}
