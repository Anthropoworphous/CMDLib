package com.github.anthropoworphous.commandlib.adaptor;

import com.github.anthropoworphous.commandlib.CMDLib;
import com.github.anthropoworphous.commandlib.arg.BaseTypes;
import main.structure.tree.IConnectable;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class CMDLimiter<T> implements IConnectable {
    /**
     * @param expectedType The expected type of this limiter
     * @param autoFill these will be used for command auto fill
     */
    public CMDLimiter(@NotNull BaseTypes expectedType, String... autoFill) {
        this.expectedType = expectedType;
        Arrays.stream(autoFill).forEach(this::addAutoFill);
    }
    /**
     * @param expectedType The expected type of this limiter
     * @param autoFill these will be used for command auto fill
     */
    public CMDLimiter(@NotNull BaseTypes expectedType, List<String> autoFill) {
        this.expectedType = expectedType;
        autoFill.forEach(this::addAutoFill);
    }
    /**
     * @param expectedType The expected type of this limiter
     */
    public CMDLimiter(@NotNull BaseTypes expectedType) {
        this.expectedType = expectedType;
    }

    //auto complete
    private T value;
    private final BaseTypes expectedType;
    private final List<Supplier<List<String>>> autoFill = new ArrayList<>();
    private boolean isWhiteList = true;
    private final List<T> limits = new ArrayList<>();
    private final List<Function<T, Boolean>> checks = new ArrayList<>();

    /**
     * make sure the user input fit the limits
     * @param input the arg this limiter limit
     * @return true if input is within the limit
     */
    public boolean validate(String input) {
        value = expectedType.stringToArgType(input);

        if (CMDLib.logDetails()) {
            CMDLib.log("-Validating " + input);
        }

        if (input == null) {
            if (CMDLib.logDetails()) {
                CMDLib.log("-\tNull input...");
            }
        } else if (value == null) {
            if (CMDLib.logDetails()) {
                CMDLib.log("-\tCould not pharse input to " + expectedType.getReadableName());
            }
        } else if (!checks.stream().allMatch(function -> function.apply(value))) {
            if (CMDLib.logDetails()) {
                CMDLib.log("-\tInput didn't pass all the checks");
            }
        } else if (limits.size() != 0 && limits.contains(value) != isWhiteList) {
            if (CMDLib.logDetails()) {
                CMDLib.log("-\tInput is " + ((isWhiteList) ?
                        "not in the whitelist" :
                        "in the blacklist"));
            }
        } else {
            return true;
        }
        return false;
    }

    public CMDLimiter<T> whitelist(boolean bool) { this.isWhiteList = bool; return this; }
    public CMDLimiter<T> addLimits(T limit) { limits.add(limit); return this; }
    public CMDLimiter<T> addLimits(Collection<T> limit) { limits.addAll(limit); return this; }
    public CMDLimiter<T> addChecks(Function<T, Boolean> check) { this.checks.add(check); return this; }
    public CMDLimiter<T> addAutoFill(String fillWith) { this.autoFill.add(() ->
            Collections.singletonList(fillWith)); return this; }
    public CMDLimiter<T> addAutoFill(Supplier<List<String>> fillWith) { this.autoFill.add(fillWith); return this; }
    public CMDLimiter<T> addAutoFillSingle(Supplier<String> fillWith) {
        this.autoFill.add(() -> Collections.singletonList(fillWith.get()));
        return this;
    }

    public BaseTypes getExpectedType() {
        return expectedType;
    }
    public List<T> getLimit() {
        return limits;
    }
    public boolean isWhiteList() {
        return isWhiteList;
    }
    public List<String> getAutoFill() { return autoFill.stream()
            .flatMap(f -> f.get().stream().filter(this::validate))
            .collect(Collectors.toList());
    }

    @NotNull public T getValue(String input) {
        return value;
    }
}
