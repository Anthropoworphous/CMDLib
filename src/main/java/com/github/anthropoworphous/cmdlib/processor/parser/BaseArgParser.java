package com.github.anthropoworphous.cmdlib.processor.parser;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class BaseArgParser<T> implements ArgParser<T> {
    public BaseArgParser(ArgType<T> expectedType, String... autoFill) {
        this.expectedType = expectedType;
        Arrays.stream(autoFill).forEach(this::addAutoFill);
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
    private final List<Function<T, Boolean>> checks = new ArrayList<>();

    @Override
    public ArgType<T> getArgType() {
        return expectedType;
    }

    /**
     * make sure the user input fit the limits
     * @param input the arg this limiter limit
     * @throws IllegalArgumentException throw if input is illegal in any way
     */
    @Override
    public T parse(String input) throws IllegalArgumentException {
        T value;

        //null input
        if (input == null) {  throw new IllegalArgumentException("Null input"); }

        //failed to phrase
        value = expectedType.stringToArgType(input).orElseThrow(() ->
                new IllegalArgumentException("Could not phrase input to " + expectedType.readableName()));

        //Not in whitelist or in blacklist
        if (whitelist.size() != 0 && !whitelist.contains(value)) {
            throw new IllegalArgumentException("Input is not in the whitelist");
        }
        if (blacklist.size() != 0 && blacklist.contains(value)) {
            throw new IllegalArgumentException("Input is in the blacklist");
        }

        //failed to pass additional checks
        if (!checks.stream().allMatch(function -> function.apply(value))) {
            throw new IllegalArgumentException("Input didn't pass all the checks");
        }

        return value;
    }
    @Override
    public boolean validation(String input) {
        try {
            parse(input);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void addToWhitelist(T input) { whitelist.add(input); }
    @Override
    public void addToWhitelist(Collection<? extends T> input) { whitelist.addAll(input); }
    @Override
    public void addToBlacklist(T input) { blacklist.add(input); }
    @Override
    public void addToBlacklist(Collection<? extends T> input) { blacklist.addAll(input); }
    @Override
    public void addChecks(Function<T, Boolean> check) { this.checks.add(check); }
    @Override
    public void addAutoFill(String fillWith) {
        this.autoFill.add(() -> Collections.singletonList(fillWith));
    }
    @Override
    public void addAutoFill(Supplier<List<String>> fillWith) { this.autoFill.add(fillWith); }


    @Override
    public List<String> getAutoFill() {
        return autoFill.stream()
            .flatMap(supplier -> supplier.get().stream()
                    .filter(option -> Optional.ofNullable(parse(option)).isPresent())
            ).collect(Collectors.toList());
    }

    @Override
    public List<T> getWhiteList() {
        return whitelist;
    }

    @Override
    public List<T> getBlackList() {
        return blacklist;
    }
}
