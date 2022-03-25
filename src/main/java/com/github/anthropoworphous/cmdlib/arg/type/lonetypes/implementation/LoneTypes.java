package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.implementation.BaseType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.LoneArgType;


@SuppressWarnings("unused")
public abstract class LoneTypes<T> extends BaseType<T> implements LoneArgType<T> {
    public LoneTypes(String readableName, String readableDescription) {
        super(readableName, readableDescription);
    }

    //default override for LoneTypes
    @Override

    public String argTypeToString(T input) {
        return String.valueOf(input);
    }

    @Override
    public boolean isMultiValue() {
        return false;
    }

    @Override
    public IArgParser parser() {
        return new ArgParser<>(this);
    }
}
