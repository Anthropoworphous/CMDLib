package com.github.anthropoworphous.cmdlib.command.variable.addition;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

public abstract class Addition<T> {
    public abstract Var<T> add(Var<T> var);
}
