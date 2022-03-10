package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class IntVar extends BaseTypes<Integer> {
    public IntVar() {
        super("<Integer>", "Ex: 1, 2, -3");
    }

    @Override
    public @NotNull Optional<Integer> stringToArgType(String input) {
        return Optional.of(input).map(Integer::parseInt);
    }
}
