package com.github.anthropoworphous.cmdlib.arg.route;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.LoneArgType;

import java.util.List;
import java.util.Map;

public interface IRoute {
    List<ArgType<?>> getRoute();
    List<LoneArgType<?>> getDecompressedRoute();

    List<Map.Entry<ArgType<?>, Object>> compress(List<Object> values);
}

