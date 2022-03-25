package com.github.anthropoworphous.cmdlib.arg.analyst;

import com.github.anthropoworphous.cmdlib.arg.analyst.implementation.Analyst;
import com.github.anthropoworphous.cmdlib.arg.analyst.implementation.EmptyAnalyst;
import com.github.anthropoworphous.cmdlib.arg.route.IRoute;

import java.util.List;

public interface ArgsAnalyst {
    List<IRoute> filteredRoutes();

    int getInputSize();

    <T> T get(int index);

    List<String> getAutoFill();

    boolean valid();

    static ArgsAnalyst of(List<String> input, List<IRoute> routes) {
        if (routes == null || routes.size() == 0) {
            return new EmptyAnalyst();
        } else {
            return new Analyst(input, routes);
        }
    }
}
