package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LongVar extends BaseTypes<Long> {
    public LongVar() {
        super("<Long>", "Ex: 1, 5, 9223372036854775807");
    }

    @Override
    public @NotNull Optional<Long> stringToArgType(String input) {
        return Optional.of(input).map(Long::parseLong);
    }
}
