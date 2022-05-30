package com.github.anthropoworphous.cmdlib.command.variable.single;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

import java.util.List;

public class BooleanVar extends Var<Boolean> {
    @Override
    public Boolean convert(List<String> input) throws IllegalArgumentException {
        return switch (input.get(0).toLowerCase()) {
            case "true" -> true;
            case "false" -> false;
            default -> throw new IllegalArgumentException();
        };
    }
}
