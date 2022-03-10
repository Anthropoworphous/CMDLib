package com.github.anthropoworphous.cmdlib.arg.analyst;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.types.ObjectVar;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import main.structure.tree.Connected;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record Analyst(List<String> input, Set<List<Connected<ArgType<?>>>> routes) implements ArgsAnalyst {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        T result = null;

        //+ 1 for root node
        try {
            result = (T) getParser().get(index).getArgType()
                    .stringToArgType(input.get(index))
                    .orElseThrow(); //handled in ArgParser
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean validate() {
        return routes.size() == 1;
    }

    @Override
    public List<ArgParser<?>> getParser() {
        return validate() ?
                routes.stream().findFirst().orElseThrow()
                        .stream()
                        .map(connected -> connected.value()
                                .orElse(new ObjectVar())
                                .parser()
                        )
                        .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public int getInputSize() {
        return input.size();
    }

    @Override
    public List<String> getAutoFill() {
        return routes.stream()
                .filter(route -> route.size() >= getInputSize()) //filter out those that are too short
                .flatMap(list -> list
                        //get the arg type responsible for the current input
                        .get(getInputSize() - 1)
                        .value()
                        .orElseThrow(() -> new IllegalArgumentException("Missing ArgType"))
                        .parser()
                        .getAutoFill()
                        .stream()
                        //filter the provided autofill with the current string
                        .filter(completion -> completion.toLowerCase().contains(input.get(input.size() - 1)))
                ).collect(Collectors.toList());
    }
}
