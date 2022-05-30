package com.github.anthropoworphous.cmdlib.command.variable.addition.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import com.github.anthropoworphous.cmdlib.command.variable.addition.Addition;
import com.github.anthropoworphous.cmdlib.command.variable.addition.VarFacade;

import java.util.List;
import java.util.function.Function;

public class AutoComplete<T> extends Addition<T> {
    private final Function<String, List<String>> autoComplete;

    public AutoComplete(Function<String, List<String>> autoComplete) {
        this.autoComplete = autoComplete;
    }

    @Override
    public Var<T> add(Var<T> var) {
        return new VarFacade<>(var) {
            @Override
            public List<String> autoComplete(String input) {
                return autoComplete.apply(input);
            }
        };
    }
}
