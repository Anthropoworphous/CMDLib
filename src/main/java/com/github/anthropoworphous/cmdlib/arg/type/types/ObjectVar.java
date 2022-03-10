package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ObjectVar extends BaseTypes<Object> {
    public ObjectVar() {
        super("<Object>", "Ex: 1, e, True");
    }

    @Override
    public @NotNull Optional<Object> stringToArgType(String input) {
        return Optional.of(input);
    }
}
