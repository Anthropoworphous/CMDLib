package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;

import java.util.Optional;

public class IntVar extends LoneTypes<Integer> {
    public IntVar() {
        super("<Integer>", "Ex: 1, 2, -3");
    }

    @Override
    public Optional<Integer> stringToArgType(String input) {
        return Optional.of(input).map(Integer::parseInt);
    }
}
