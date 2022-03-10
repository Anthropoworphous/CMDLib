package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class StringVar extends BaseTypes<String> {
    public StringVar() {
        super("<String>", "Ex: abc, owo, stuff");
    }

    @Override
    public @NotNull Optional<String> stringToArgType(String input) {
        return Optional.of(input);
    }
}
