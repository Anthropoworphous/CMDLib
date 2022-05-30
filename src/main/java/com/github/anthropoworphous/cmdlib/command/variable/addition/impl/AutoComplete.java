package com.github.anthropoworphous.cmdlib.command.variable.addition.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import com.github.anthropoworphous.cmdlib.command.variable.addition.Addition;
import com.github.anthropoworphous.cmdlib.command.variable.addition.VarFacade;

import java.util.List;
import java.util.function.Supplier;

public class AutoComplete<T> extends Addition<T> {
    private final Supplier<List<String>> autoComplete;

    public AutoComplete(Supplier<List<String>> autoComplete) {
        this.autoComplete = autoComplete;
    }

    @Override
    public Var<T> add(Var<T> var) {
        return new VarFacade<>(var) {
            @Override
            public List<String> autoComplete() {
                return autoComplete.get();
            }
        };
    }
}
