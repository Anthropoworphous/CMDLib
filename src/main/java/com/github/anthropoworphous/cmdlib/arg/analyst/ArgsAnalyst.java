package com.github.anthropoworphous.cmdlib.arg.analyst;

import com.github.anthropoworphous.cmdlib.arg.route.IRoute;

import java.util.List;

public interface ArgsAnalyst {
    List<IRoute> filteredRoutes();
    int getInputSize();
    <T> T get(int index);
    List<String> getAutoFill();
    boolean valid();
}
