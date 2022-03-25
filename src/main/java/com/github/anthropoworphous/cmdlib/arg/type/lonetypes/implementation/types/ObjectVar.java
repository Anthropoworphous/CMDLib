package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;

import java.util.Optional;

public class ObjectVar extends LoneTypes<Object> {
    public ObjectVar() {
        super("<Object>", "Ex: 1, e, True");
    }

    @Override
    public Optional<Object> stringToArgType(String input) {
        return Optional.of(input);
    }
}
