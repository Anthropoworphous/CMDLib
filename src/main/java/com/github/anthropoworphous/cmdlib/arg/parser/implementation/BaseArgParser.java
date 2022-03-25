package com.github.anthropoworphous.cmdlib.arg.parser.implementation;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BaseArgParser<T> implements IArgParser<T> {
    public BaseArgParser(ArgType<T> expectedType, String... autoFill) {
        this.expectedType = expectedType;
        if (autoFill != null && autoFill.length > 0) {
            Arrays.stream(autoFill).forEach(this::addAutoFill);
        }
    }
    public BaseArgParser(ArgType<T> expectedType, List<String> autoFill) {
        this.expectedType = expectedType;
        autoFill.forEach(this::addAutoFill);
    }
    public BaseArgParser(ArgType<T> expectedType, Supplier<List<String>> autoFill) {
        this.expectedType = expectedType;
        autoFill.get().forEach(this::addAutoFill);
    }
    public BaseArgParser(ArgType<T> expectedType) {
        this.expectedType = expectedType;
    }

    //auto complete
    private final ArgType<T> expectedType;
    private final List<Supplier<List<String>>> autoFill = new ArrayList<>();
    private final List<T> whitelist = new ArrayList<>();
    private final List<T> blacklist = new ArrayList<>();
    private final List<Predicate<T>> checks = new ArrayList<>();

    public abstract List<String> getAutoFill(List<String> unfiltered);

    @Override
    public ArgType<T> getArgType() {
        return expectedType;
    }
    @Override
    public List<T> getWhitelist() { return whitelist; }
    @Override
    public List<T> getBlacklist() { return blacklist; }

    @Override
    public final List<String> getAutoFill() {
        return getAutoFill(autoFill.stream().flatMap(p -> p.get().stream()).collect(Collectors.toList()));
    }

    protected List<Predicate<T>> getChecks() { return checks; }

    @Override
    public void addAutoFill(String fillWith) {
        autoFill.add(() -> Collections.singletonList(fillWith));
    }
    @Override
    public void addAutoFill(Supplier<List<String>> fillWith) { this.autoFill.add(fillWith); }

    @Override
    public void addToWhitelist(T input) { whitelist.add(input); }
    @Override
    public void addToWhitelist(Collection<? extends T> input) { whitelist.addAll(input); }
    @Override
    public void addToBlacklist(T input) { blacklist.add(input); }
    @Override
    public void addToBlacklist(Collection<? extends T> input) { blacklist.addAll(input); }
    @Override
    public void addChecks(Predicate<T> check) { this.checks.add(check); }
}
