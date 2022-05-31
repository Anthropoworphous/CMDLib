package com.github.anthropoworphous.cmdlib.command.variable;

import com.github.anthropoworphous.cmdlib.command.variable.addition.Addition;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public abstract class Var<T> {
    // abstract
    /**
     * Var override this to convert string into data.
     * Additional stuff override this to add logics.
     * Don't use this anywhere else.
     * @param input string from command
     * @return data
     * @throws IllegalArgumentException for many reasons...
     */
    public abstract T convert(List<String> input) throws IllegalArgumentException;

    // default
    public int size() { return 1; }
    public List<String> autoComplete() { return Collections.emptyList(); }

    // final
    public final T get(Input input) throws IndexOutOfBoundsException, IllegalArgumentException {
        return convert(input.slice(size()));
    }
    public final Stream<String> getAutoComplete(String input) {
        return autoComplete().stream().filter(str -> str.startsWith(input));
    }
    public final Var<T> additional(Addition<T> addition) {
        return addition.add(this);
    }

    public static class Input {
        private final List<String> input;
        private int sliceIndex = 0;

        public Input(String... input) {
            this.input = List.of(input);
        }
        public Input(List<String> input) {
            this.input = input;
        }

        public int size() { return input.size(); }
        public List<String> slice(int amount) throws IndexOutOfBoundsException {
            return input.subList(sliceIndex, sliceIndex+=amount);
        }
    }
}
