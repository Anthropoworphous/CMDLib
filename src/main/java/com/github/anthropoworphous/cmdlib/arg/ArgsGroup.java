package com.github.anthropoworphous.cmdlib.arg;

import com.github.anthropoworphous.cmdlib.arg.analyst.Analyst;
import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import main.structure.tree.Connected;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Need to be optimised...
 */
@SuppressWarnings({"unused"})
public class ArgsGroup implements Args {
    public ArgsGroup(@Nullable List<Connected<ArgType<?>>> argTypes, @Nullable String[] currentInputs, boolean finished) {
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
    public static Set<List<Connected<ArgType<?>>>> routeFilter(
            List<Connected<ArgType<?>>> argTypes,
            List<String> currentInputs,
            boolean finished
    ) {
        return argTypes.stream()
                .map(connected -> connected.multiLayerFilter(buildFilters(currentInputs), finished))
                .flatMap(list -> list.stream().map(child -> child.slimTree(false)))
                .collect(Collectors.toSet());
    }

    public static List<Function<ArgType<?>, Boolean>> buildFilters(
            List<String> currentInputs
    ) {
        List<Function<ArgType<?>, Boolean>> filters = new ArrayList<>();
        for (String input : currentInputs) {
            filters.add(type -> (type.parser().validation(input)));
        }
        return filters;
    }
}