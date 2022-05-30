package com.github.anthropoworphous.cmdlib.command.variable.addition.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import com.github.anthropoworphous.cmdlib.command.variable.addition.Addition;
import com.github.anthropoworphous.cmdlib.command.variable.addition.VarFacade;

import java.util.List;
import java.util.function.Supplier;

public class Blacklist<T> extends Addition<T> {
    private final Supplier<List<T>> blacklist;

    public Blacklist(Supplier<List<T>> blacklist) {
        this.blacklist = blacklist;
    }

    @Override
    public Var<T> add(Var<T> var) {
        return new VarFacade<>(var) {
            @Override
            public T convert(List<String> input) throws IllegalArgumentException {
                T result = var.convert(input);
                if (blacklist.get().contains(result)) { throw new IllegalArgumentException(); }
                return result;
            }
        };
    }
}
