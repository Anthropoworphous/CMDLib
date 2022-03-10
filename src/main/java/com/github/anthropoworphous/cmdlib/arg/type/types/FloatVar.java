package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FloatVar extends BaseTypes<Float> {
    public FloatVar() {
        super("<Float>", "Ex: 1.2, -6.9, 4.20");
    }

    @Override
    public @NotNull Optional<Float> stringToArgType(String input) {
        return Optional.of(input).map(Float::parseFloat);
    }
}
