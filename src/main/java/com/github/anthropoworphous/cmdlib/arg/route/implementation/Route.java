package com.github.anthropoworphous.cmdlib.arg.route.implementation;

import com.github.anthropoworphous.cmdlib.arg.route.IRoute;
import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.LoneArgType;
import com.github.anthropoworphous.cmdlib.arg.type.multitypes.MultiValueArgType;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class Route implements IRoute {
    public Route(ArgType<?>... route) {
        this.route = Arrays.asList(route);
        decompressedRoute = decompress();
    }

    private final List<ArgType<?>> route;
    private final List<LoneArgType<?>> decompressedRoute;

    @Override
    public List<ArgType<?>> getRoute() {
        return route;
    }

    @Override
    public List<LoneArgType<?>> getDecompressedRoute() {
        return decompressedRoute;
    }

    @Override
    public List<Pair<ArgType<?>, Object>> compress(List<Object> values) {
        List<Pair<ArgType<?>, Object>> result = new ArrayList<>();
        int index = 0;

        for (ArgType<?> arg : route) {
            if (arg.isMultiValue()) {
                result.add(new Pair<>(
                        arg, resolveArgCompression(
                                (MultiValueArgType<?>) arg,
                                values.subList(index, values.size())
                        )
                ));
                int extra = ((MultiValueArgType<?>) arg).separate().size();
                index += extra;
            } else {
                result.add(new Pair<>(arg, values.get(index++)));
            }
        }

        return result;
    }

    //helper method
    private List<LoneArgType<?>> decompress() {
        List<LoneArgType<?>> result = new ArrayList<>();
        for (ArgType<?> c : route) {
            result.addAll(extract(c));
        }
        return result;
    }

    private List<LoneArgType<?>> extract(ArgType<?> argType) {
        return argType.isMultiValue() ?
                ((MultiValueArgType<?>) argType).separate()
                        .stream()
                        .flatMap(a -> extract(a).stream())
                        .collect(Collectors.toList()) :
                Collections.singletonList((LoneArgType<?>) argType);
    }

    private Object resolveArgCompression(MultiValueArgType<?> arg, List<Object> values) {
        List<Object> material = new ArrayList<>();
        int index = 0;

        for (ArgType<?> dividedArg : arg.separate()) {
            if (dividedArg.isMultiValue()) {
                material.add(resolveArgCompression(
                        (MultiValueArgType<?>) dividedArg,
                        values.subList(index, values.size())
                ));
                index += extract(dividedArg).size();
            } else {
                material.add(values.get(index++));
            }
        }

        return arg.combine(material).orElseThrow(() -> new RuntimeException("Failed to combine multi value arg"));
    }
}




















