package com.github.anthropoworphous.commandlib.arg;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"unused"})
public class Args {
    public Args(String[] reallyBadArgs, List<ArgsLimiter<?>> limiters) {
        badArgs = List.of(reallyBadArgs);
        this.limiters = limiters;

        if (limiters == null && badArgs.size() == 0) { valid = true; }
        else if (limiters == null) { invalidReason = "Too much arguments"; }
        else if (limiters.size() != badArgs.size()) { invalidReason = "Not enough or too much arguments"; }
        else {
            Iterator<ArgsLimiter<?>> it1 = limiters.iterator();
            Iterator<String> it2 = badArgs.iterator();

            while (it1.hasNext()) {
                ArgsLimiter<?> limiter = it1.next();
                String input = it2.next();

                if (limiter != null && !limiter.validate(input)) {
                    invalidReason = "Invalid input: " + input; return;
                }
            }
            valid = true;
        }
    }

    private final List<String> badArgs;
    private boolean valid = false;
    private String invalidReason;
    private final List<ArgsLimiter<?>> limiters;

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
        return (limiters.get(index) == null) ?
                badArgs.get(index) :
                limiters.get(index).getValue(badArgs.get(index));
    }
    @NotNull public String getString(int index) {
        return badArgs.get(index);
    }
    public int getInt(int index) {
        return (int) limiters.get(index).getValue(badArgs.get(index));
    }
    public double getDouble(int index) {
        return (double) limiters.get(index).getValue(badArgs.get(index));
    }
    public float getFloat(int index) {
        return (float) limiters.get(index).getValue(badArgs.get(index));
    }
    public boolean getBoolean(int index) {
        return (boolean) limiters.get(index).getValue(badArgs.get(index));
    }
}
