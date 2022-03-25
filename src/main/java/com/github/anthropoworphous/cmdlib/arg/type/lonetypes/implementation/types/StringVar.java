package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;

import java.util.Optional;

public class StringVar extends LoneTypes<String> {
    public StringVar() {
        super("<String>", "Ex: abc, owo, stuff");
    }

    @Override
    public Optional<String> stringToArgType(String input) {
        return Optional.of(input);
    }
}
