package com.github.anthropoworphous.commandlib.arg;

import org.jetbrains.annotations.NotNull;

import java.util.List;


@SuppressWarnings("unused")
public class ArgsLimiter<T> {
    public ArgsLimiter(@NotNull ArgsType expectedType, List<T> limits, boolean isWhiteList) {
        this.expectedType = expectedType;
        this.limits = limits;
        this.isWhiteList = isWhiteList;
    }
    public ArgsLimiter(@NotNull ArgsType expectedType, List<T> limits) {
        this.expectedType = expectedType;
        this.limits = limits;
        isWhiteList = true;
    }
    public ArgsLimiter(@NotNull ArgsType expectedType) {
        this.expectedType = expectedType;
        this.limits = null;
        isWhiteList = true;
    }

    private final ArgsType expectedType;
    private final List<T> limits;
    private final boolean isWhiteList;
    private T value;

    public ArgsType getExpectedType() {
        return expectedType;
    }

    public List<T> getLimit() {
        return limits;
    }

    public boolean isWhiteList() {
        return isWhiteList;
    }

    @NotNull public T getValue(String input) {
        return value;
    }

    public boolean validate(String input) {
        value = expectedType.stringToArgType(input);
        return (input != null
                && value != null
                && (limits == null || limits.contains(value) == isWhiteList));
    }
}
