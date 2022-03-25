package com.github.anthropoworphous.cmdlib.arg.analyst.implementation;

import com.github.anthropoworphous.cmdlib.arg.analyst.ArgsAnalyst;
import com.github.anthropoworphous.cmdlib.arg.route.IRoute;

import java.util.List;

public class EmptyAnalyst implements ArgsAnalyst {
    @Override
    public List<IRoute> filteredRoutes() {
        return null;
    }

    @Override
    public int getInputSize() {
        return 0;
    }

    @Override
    public <T> T get(int index) {
        throw new IllegalStateException("This command has no args");
    }

    @Override
    public List<String> getAutoFill() {
        return null;
    }

    @Override
    public boolean valid() {
        return true;
    }
}
