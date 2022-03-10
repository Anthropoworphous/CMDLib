package com.github.anthropoworphous.cmdlib.processor.parser;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ModifiableParser<T> {
    void addToWhitelist(T input);
    void addToWhitelist(Collection<? extends T> input);
    void addToBlacklist(T input);
    void addToBlacklist(Collection<? extends T> input);
    void addChecks(Function<T, Boolean> check);
    void addAutoFill(String fillWith);
    void addAutoFill(Supplier<List<String>> fillWith);
}
