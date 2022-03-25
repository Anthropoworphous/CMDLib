package com.github.anthropoworphous.cmdlib.arg.route;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.LoneArgType;
import javafx.util.Pair;

import java.util.List;

public interface IRoute {
    List<ArgType<?>> getRoute();

    List<LoneArgType<?>> getDecompressedRoute();

    List<Pair<ArgType<?>, Object>> compress(List<Object> values);
}

