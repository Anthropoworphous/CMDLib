package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BooleanVar extends BaseTypes<Boolean> {
    public BooleanVar() {
        super("<Boolean>", "True/False");
    }

    @Override
    public @NotNull Optional<Boolean> stringToArgType(String input) {
        return Optional.of(input).map(Boolean::parseBoolean);
    }

    @Override
    public @NotNull ArgParser<Boolean> parser() {
        return new BaseArgParser<>(this, "true", "false");
    }
}
