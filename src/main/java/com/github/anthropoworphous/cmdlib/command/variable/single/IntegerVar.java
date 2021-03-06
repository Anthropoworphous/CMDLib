package com.github.anthropoworphous.cmdlib.command.variable.single;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

import java.util.List;

public class IntegerVar extends Var<Integer> {
    @Override
    public Integer convert(List<String> input) throws IllegalArgumentException {
        try {
            return Integer.parseInt(input.get(0));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
