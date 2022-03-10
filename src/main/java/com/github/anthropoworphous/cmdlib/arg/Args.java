package com.github.anthropoworphous.cmdlib.arg;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.types.ObjectVar;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import main.structure.tree.Connected;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Need to be optimised...
 */
@SuppressWarnings({"unused"})
public class Args implements ArgsGroup {
    public Args(@Nullable List<Connected<ArgType<?, ?>>> argTypes, @Nullable String[] currentInputs, boolean finished) {
        if ((currentInputs == null || currentInputs.length == 0) && (argTypes == null || argTypes.size() == 0)) {
            analyst = new Analyst(Collections.emptyList(), Collections.emptySet());
        } else {
            assert argTypes != null; //checked in the if statement
            assert currentInputs != null; //checked in the if statement
            List<String> input = Arrays.asList(currentInputs);
            if (!finished) {
                input.remove(input.size() - 1);
            }
            analyst = new Analyst(Arrays.asList(currentInputs), routeFilter(argTypes, input, finished));
        }
    }

    //field
    private final ArgsAnalyst analyst;
    //end

    //getter
    @Override
    public ArgsAnalyst getAnalyst() {
        return analyst;
    }
    //end

    /**
     * filter all the routes
     * @param argTypes all the routes
     * @param currentInputs what the player have inputted
     * @param finished whether the player have finished typing or not
     * @return return all possible routes set(path(connected(ArgType)))
     */
    public static Set<List<Connected<ArgType<?, ?>>>> routeFilter(
            List<Connected<ArgType<?, ?>>> argTypes,
            List<String> currentInputs,
            boolean finished
    ) {
        return argTypes.stream()
                .map(connected -> connected.multiLayerFilter(buildFilters(argTypes, currentInputs), finished))
                .flatMap(list -> list.stream().map(child -> child.slimTree(false)))
                .collect(Collectors.toSet());
    }

    public static List<Function<ArgType<?, ?>, Boolean>> buildFilters(
            List<Connected<ArgType<?, ?>>> argTypes,
            List<String> currentInputs
    ) {
        List<Function<ArgType<?, ?>, Boolean>> filters = new ArrayList<>();
        int index = 0;
        for (Connected<ArgType<?, ?>> argType : argTypes) {
            String input = currentInputs.get(index++);
            filters.add(value -> (value.parser().validation(input)));
        }
        return filters;
    }


    //analyst
    public record Analyst(List<String> input,
                          Set<List<Connected<ArgType<?, ?>>>> routes)
            implements ArgsAnalyst
    {
        @Override
        public <T> T get(Class<T> c, int index) {
            //+ 1 for root node
            Object obj = getParser().get(index).getArgType().stringToArgType(input.get(index))
                    .orElseThrow(() -> new IllegalArgumentException("Unable to cast to type: " + c.toString()));
            if (!c.isInstance(obj)) {
                throw new IllegalArgumentException("Type doesn't match parser type: "
                        + getParser().get(index).getArgType().readableName());
            }
            return c.cast(obj);
        }

        @Override
        public boolean validate() {
            return routes.size() == 1;
        }

        @Override
        public List<ArgParser<?, ?>> getParser() {
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
}