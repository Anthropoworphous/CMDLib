package com.github.anthropoworphous.cmdlib.command.variable.single;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

import java.util.List;

public class DoubleVar extends Var<Double> {
    @Override
    public Double convert(List<String> input) throws IllegalArgumentException {
        try {
            return Double.parseDouble(input.get(0));
        } catch(NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
