package com.github.anthropoworphous.cmdlib.command.variable.addition.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import com.github.anthropoworphous.cmdlib.command.variable.addition.Addition;
import com.github.anthropoworphous.cmdlib.command.variable.addition.VarFacade;

import java.util.List;
import java.util.function.Supplier;

public class Check<T> extends Addition<T> {
    private final Supplier<Boolean> check;

    public Check(Supplier<Boolean> check) {
        this.check = check;
    }

    @Override
    public Var<T> add(Var<T> var) {
        return new VarFacade<>(var) {
            @Override
            public T convert(List<String> input) throws IllegalArgumentException {
                T result = var.convert(input);
                if (!check.get()) { throw new IllegalArgumentException(); }
                return result;
            }
        };
    }
}
