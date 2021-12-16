package com.github.anthropoworphous.commandlib.arg;

import com.github.anthropoworphous.commandlib.adaptor.CMDLimiter;
import main.structure.Connected;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
public class Args {
    public Args(String[] reallyBadArgs, Collection<Connected<CMDLimiter<?>>> limiter) {
        badArgs = List.of(reallyBadArgs);

        if (limiter == null && badArgs.size() == 0) { valid = true; }
        else if (limiter == null) { invalidReason = "Command do not accept arguments"; }
        else {
            List<Function<CMDLimiter<?>, Boolean>> filters = new ArrayList<>();
            badArgs.forEach(arg -> filters.add(l -> l.validate(arg)));
            List<Connected<CMDLimiter<?>>> matches =
                    limiter.stream()
                            .flatMap(l -> l.multiLayerFilter(filters, 0).stream())
                            .collect(Collectors.toList());


            if (matches.size() == 0) {
                invalidReason = "Invalid arguments"; return;
            } else if (matches.size() > 1) {
                invalidReason = "Match multiple path"; return;
            }

            Connected<CMDLimiter<?>> match = matches.get(0);
            do {
                this.limiter.add(0, match.getValue());
                match = match.getParent();
            } while (match != null);
            valid = true;
        }
    }

    private final List<String> badArgs;
    private boolean valid = false;
    private String invalidReason;
    private final List<CMDLimiter<?>> limiter = new ArrayList<>();

    public boolean isValid() {
        return valid;
    }
    public String invalidReason() {
        return invalidReason;
    }
    public int getSize() {
        return (badArgs != null) ? badArgs.size() : 0;
    }

    @NotNull public Object get(int index) {
        return (limiter.get(index) == null) ?
                badArgs.get(index) :
                limiter.get(index).getValue(badArgs.get(index));
    }
    @NotNull public String getString(int index) {
        return badArgs.get(index);
    }
    public int getInt(int index) {
        return (int) limiter.get(index).getValue(badArgs.get(index));
    }
    public double getDouble(int index) {
        return (double) limiter.get(index).getValue(badArgs.get(index));
    }
    public float getFloat(int index) {
        return (float) limiter.get(index).getValue(badArgs.get(index));
    }
    public boolean getBoolean(int index) {
        return (boolean) limiter.get(index).getValue(badArgs.get(index));
    }
}
