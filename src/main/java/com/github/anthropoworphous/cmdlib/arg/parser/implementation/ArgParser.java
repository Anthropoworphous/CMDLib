package com.github.anthropoworphous.cmdlib.arg.parser.implementation;

import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.LoneArgType;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ArgParser<T> extends BaseArgParser<T> {
    public ArgParser(LoneArgType<T> expectedType, String... autoFill) {
        super(expectedType, autoFill);
    }

    public ArgParser(LoneArgType<T> expectedType, List<String> autoFill) {
        super(expectedType, autoFill);
    }

    public ArgParser(LoneArgType<T> expectedType, Supplier<List<String>> autoFill) {
        super(expectedType, autoFill);
    }

    public ArgParser(LoneArgType<T> expectedType) {
        super(expectedType);
    }

    /**
     * make sure the user input fit the limits
     *
     * @param input the arg this limiter limit
     * @throws IllegalArgumentException throw if input is illegal in any way
     */
    @Override
    public T parse(String input) throws IllegalArgumentException {
        LoneArgType<T> expectedType = (LoneArgType<T>) getArgType();
        T value;

        //null input
        if (input == null) {
            throw new IllegalArgumentException("Null input");
        }

        //failed to phrase
        value = expectedType.stringToArgType(input).orElseThrow(() ->
                new IllegalArgumentException("Could not phrase input to " + expectedType.readableName()));

        //Not in whitelist or in blacklist
        if (getWhitelist().size() != 0 && !getWhitelist().contains(value)) {
            throw new IllegalArgumentException("Input is not in the whitelist");
        }
        if (getBlacklist().size() != 0 && getBlacklist().contains(value)) {
            throw new IllegalArgumentException("Input is in the blacklist");
        }

        //failed to pass additional checks
        if (!getChecks().stream().allMatch(function -> function.test(value))) {
            throw new IllegalArgumentException("Input didn't pass all the checks");
        }

        return value;
    }

    @Override
    public boolean validation(String input) {
        try {
            parse(input);
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getAutoFill(List<String> unfiltered) {
        return unfiltered
                .stream()
                .filter(option -> Optional.ofNullable(parse(option)).isPresent())
                .collect(Collectors.toList());
    }
}
