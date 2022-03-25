package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;

import java.util.Optional;

public class DoubleVar extends LoneTypes<Double> {
    public DoubleVar() {
        super("<Double>", "Ex: 1.2, -6.9, 4.20");
    }

    @Override
    public Optional<Double> stringToArgType(String input) {
        return Optional.of(input).map(Double::parseDouble);
    }
}
