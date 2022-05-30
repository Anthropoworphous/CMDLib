package com.github.anthropoworphous.cmdlib.command.variable.addition;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

import java.util.List;

public class VarFacade<T> extends Var<T> {
    private final Var<T> var;

    public VarFacade(Var<T> var) {
        this.var = var;
    }

    // Facade
    @Override
    public T convert(List<String> input) throws IllegalArgumentException {
        return var.convert(input);
    }
    @Override
    public int size() {
        return var.size();
    }
    @Override
    public List<String> autoComplete() {
        return var.autoComplete();
    }
}
