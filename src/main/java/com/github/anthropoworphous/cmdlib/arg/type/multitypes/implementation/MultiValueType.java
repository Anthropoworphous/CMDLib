package com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.implementation.BaseType;
import com.github.anthropoworphous.cmdlib.arg.type.multitypes.MultiValueArgType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class MultiValueType<T> extends BaseType<T> implements MultiValueArgType<T> {
    public MultiValueType(String readableName, String readableDescription) {
        super(readableName, readableDescription);
    }

    private final List<T> whitelist = new ArrayList<>();
    private final List<T> blacklist = new ArrayList<>();
    private final List<Predicate<T>> checks = new ArrayList<>();


    public abstract List<ArgType<?>> divideInto();
    public abstract Optional<T> combineInto(List<Object> input);

    @Override
    public @NotNull List<T> whitelist() {
        return whitelist;
    }
    @Override
    public @NotNull List<T> blacklist() {
        return blacklist;
    }

    @Override
    public boolean isMultiValue() {
        return true;
    }
    @Override
    public final @NotNull List<ArgType<?>> separate() {
        List<ArgType<?>> divided = divideInto();
        divided.forEach(d -> d.dividedFrom(this));
        return divided;
    }
    @Override
    public final @NotNull Optional<T> combine(List<Object> input) {
        if (input.size() != separate().size()) {
            return Optional.empty();
        }
        return combineInto(input);
    }
}
