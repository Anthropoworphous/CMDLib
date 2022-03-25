package com.github.anthropoworphous.cmdlib.arg.parser;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface ModifiableParser<T> {
    void addToWhitelist(T input);

    void addToWhitelist(Collection<? extends T> input);

    void addToBlacklist(T input);

    void addToBlacklist(Collection<? extends T> input);

    void addChecks(Predicate<T> check);

    void addAutoFill(String fillWith);

    void addAutoFill(Supplier<List<String>> fillWith);
}
