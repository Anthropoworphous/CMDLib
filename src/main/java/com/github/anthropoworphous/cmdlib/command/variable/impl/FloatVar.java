package com.github.anthropoworphous.cmdlib.command.variable.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

import java.util.List;

public class FloatVar extends Var<Float> {
    @Override
    public Float convert(List<String> input) throws IllegalArgumentException {
        try {
            return Float.parseFloat(input.get(0));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
