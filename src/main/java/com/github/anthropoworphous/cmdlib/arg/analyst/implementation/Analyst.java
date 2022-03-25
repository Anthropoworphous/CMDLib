package com.github.anthropoworphous.cmdlib.arg.analyst.implementation;

import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.route.IRoute;
import com.github.anthropoworphous.cmdlib.arg.type.ArgType;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Analyst implements ArgsAnalyst {
    public Analyst(List<String> input, List<IRoute> routes) {
        this.input = input;
        this.routes = routes;
        valid = filterRoutes();
    }

    private List<IRoute> routes;
    private final List<String> input;
    private final boolean valid;
    private List<Map.Entry<ArgType<?>, Object>> values = null;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        T result = null;

        try {
            result = (T) values.get(index).getValue();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<IRoute> filteredRoutes() {
        return routes;
    }

    @Override
    public int getInputSize() {
        return input.size();
    }

    @Override
    public List<String> getAutoFill() {
        return routes.stream()
                .map(IRoute::getDecompressedRoute)
                .filter(route -> route.size() >= getInputSize()) //filter out those that are too short
                .flatMap(route -> route
                        //get the arg type responsible for the current input
                        .get(getInputSize() - 1)
                        .parser()
                        .getAutoFill()
                        .stream()
                        //filter the provided autofill with the current string
                        .filter(completion -> completion.toLowerCase().contains(input.get(input.size() - 1)))
                ).collect(Collectors.toList());
    }

    @Override
    public boolean valid() {
        return valid;
    }

    //helper
    private boolean filterRoutes() {
        if (input != null && input.size() != 0) {
            routes = routes.stream()
                    .filter(route -> {
                        if (route.getDecompressedRoute().size() != input.size()) { return false; }
                        Iterator<String> str = input.iterator();
                        return route.getDecompressedRoute()
                                .stream()
                                .anyMatch(c -> c.parser().validation(str.next()));
                    })
                    .collect(Collectors.toList());
            if (routes.size() == 1) {
                mapValue(routes.get(0));
                return true;
            }
        }
        return false;
    }

    private void mapValue(IRoute route) {
        Iterator<String> it = input.iterator();
        values = route.compress(route.getDecompressedRoute()
                .stream()
                .map(arg -> ((IArgParser<?>) arg.parser())
                        .parse(it.next())
                )
                .collect(Collectors.toList())
        );
    }
}
