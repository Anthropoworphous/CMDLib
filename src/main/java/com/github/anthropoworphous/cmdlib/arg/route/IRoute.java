package com.github.anthropoworphous.cmdlib.arg.route;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.LoneArgType;

import java.util.List;

public interface IRoute {
    List<ArgType<?>> getRoute();
    List<LoneArgType<?>> getDecompressedRoute();

    List<Object> compress(List<Object> values);
}

