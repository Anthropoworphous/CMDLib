package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;

public abstract class CustomLoneVar<T> extends LoneTypes<T> {
    public CustomLoneVar(String readableName, String readableDescription) {
        super(readableName, readableDescription);
    }
}
