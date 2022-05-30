package com.github.anthropoworphous.cmdlib.command;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface CMD {
    List<Route> routes();
    String name();
    default Optional<String> permission() {
        return Optional.empty();
    }
    default Optional<String> description() {
        return Optional.empty();
    }
    default Optional<List<String>> aliases() {
        return Optional.empty();
    }
    default Optional<List<String>> usage() {
        return Optional.empty();
    }

    default Optional<Route> varify(String input) {
        return routes().stream().filter(route -> route.process(input)).findFirst();
    }

    class Route {
        private final Executor executor;
        private final Var<?>[] variables;
        private final List<Object> processedInput = new ArrayList<>();

        /**
         * It's guaranteed that the {@link Object} the executor get matches the variables bound
         * @param executor code that run if input matches the variables and can be converted
         * @param variables the type bound that will apply to the input
         */
        public Route(Executor executor, Var<?>... variables) {
            this.executor = executor;
            this.variables = variables;
        }

        private boolean process(String in) {
            Var.Input input = new Var.Input(in);
            if (input.size() != Arrays.stream(variables).mapToInt(Var::size).sum()) { return false; }
            try {
                for (Var<?> var : variables) {
                    processedInput.add(var.get(input));
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * @param sender command sender
         * @return execution result
         */
        public boolean execute(CommandSender sender) {
            return executor.execute(sender, processedInput);
        }

        /**
         * {@code (CommandSender, List<Object>) -> boolean}
         */
        @FunctionalInterface
        public interface Executor {
            boolean execute(CommandSender sender, List<Object> vars);
        }
    }

    class Routes {
        public static List<Route> of(Route... routes) {
            return List.of(routes);
        }
    }
}
