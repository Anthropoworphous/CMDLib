package com.github.anthropoworphous.commandlib.adaptor;

import com.github.anthropoworphous.commandlib.arg.ArgsType;
import main.structure.Connected;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public class CMDLimiter<T> {
    public CMDLimiter(@NotNull ArgsType expectedType) {
        this.expectedType = expectedType;
    }
    public static Connected<CMDLimiter<?>> newRoot() {
        //argsType doesn't matter, root is not evaluated, this is just used to hold data
        return new Connected<>(new CMDLimiter<>(ArgsType.STRING));
    }

    private T value;
    private final ArgsType expectedType;
    private boolean isWhiteList = true;
    private final List<T> limits = new ArrayList<>();
    private final List<Function<String, Boolean>> checks = new ArrayList<>();

    /**
     * make sure the user input fit the limits
     * @param input the arg this limiter limit
     * @return true if input is within the limit
     */
    public boolean validate(String input) {
        value = expectedType.stringToArgType(input);
        return (input != null
                && value != null
                && checks.stream().allMatch(function -> function.apply(input))
                && limits.contains(value) == isWhiteList);
    }

    public CMDLimiter<T> whitelist(boolean bool) { this.isWhiteList = bool; return this; }
    public CMDLimiter<T> addLimits(T limit) { limits.add(limit); return this; }
    public CMDLimiter<T> addLimits(Collection<T> limit) { limits.addAll(limit); return this; }
    public CMDLimiter<T> addChecks(Function<String, Boolean> check) { this.checks.add(check); return this; }

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
}
