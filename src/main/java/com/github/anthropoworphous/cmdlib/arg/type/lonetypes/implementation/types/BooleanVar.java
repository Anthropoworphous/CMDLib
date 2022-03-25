package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BooleanVar extends LoneTypes<Boolean> {
    public BooleanVar() {
        super("<Boolean>", "True/False");
    }

    @Override
    public @NotNull Optional<Boolean> stringToArgType(String input) {
        return Optional.of(input).map(Boolean::parseBoolean);
    }

    @Override
    public @NotNull IArgParser<Boolean> parser() {
        return new ArgParser<>(this, "true", "false");
    }
}
