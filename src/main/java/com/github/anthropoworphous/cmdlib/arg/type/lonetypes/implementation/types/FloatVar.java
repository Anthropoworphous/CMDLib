package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;

import java.util.Optional;

public class FloatVar extends LoneTypes<Float> {
    public FloatVar() {
        super("<Float>", "Ex: 1.2, -6.9, 4.20");
    }

    @Override
    public Optional<Float> stringToArgType(String input) {
        return Optional.of(input).map(Float::parseFloat);
    }
}
