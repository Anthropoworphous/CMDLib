package com.github.anthropoworphous.cmdlib.command.variable.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;

import java.util.List;

public class StringVar extends Var<String> {
    @Override
    public String convert(List<String> input) throws IllegalArgumentException {
        return input.get(0);
    }
}
