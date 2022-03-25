package com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation.MultiValueType;

public abstract class CustomMultiValueVar<T> extends MultiValueType<T> {
    public CustomMultiValueVar(String readableName, String readableDescription) {
        super(readableName, readableDescription);
    }
}
