package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DoubleVar extends BaseTypes<Double> {
    public DoubleVar() {
        super("<Double>", "Ex: 1.2, -6.9, 4.20");
    }

    @Override
    public @NotNull Optional<Double> stringToArgType(String input) {
        return Optional.of(input).map(Double::parseDouble);
    }
}
