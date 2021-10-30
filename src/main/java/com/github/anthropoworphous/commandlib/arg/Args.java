package com.github.anthropoworphous.commandlib.arg;

import java.util.Iterator;
import java.util.List;

public class Args {
    public Args(String[] reallyBadArgs, List<ArgsLimiter<?>> limiters) {
        badArgs = List.of(reallyBadArgs);
        this.limiters = limiters;

        if (limiters != null) {
            Iterator<ArgsLimiter<?>> it1 = limiters.iterator();
            Iterator<String> it2 = badArgs.iterator();
            while (it1.hasNext()) {
                if (!it2.hasNext()) {
                    valid = false;
                    break;
                }

                ArgsLimiter<?> limitor = it1.next();
                String input = it2.next();

                if (!limitor.validate(input)) {
                    valid = false;
                    break;
                }
            }
        }
    }

    private final List<String> badArgs;
    private boolean valid = true;
    private final List<ArgsLimiter<?>> limiters;

    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        if (valid && getSize() > index) {
            if (limiters.size() > index) {
                return (T) limiters.get(index).getValue(badArgs.get(index));
            } else {
                return (T) (badArgs.get(index));
            }
        } else { return null; }
    }
    public String getString(int index) {
        if (valid && getSize() > index) {
            return badArgs.get(index);
        } else { return null; }
    }
    public Integer getInt(int index) {
        if (valid && getSize() > index && limiters.size() > index) {
            return (Integer) limiters.get(index).getValue(badArgs.get(index));
        } else { return null; }
    }
    public Double getDouble(int index) {
        if (valid && getSize() > index && limiters.size() > index) {
            return (Double) limiters.get(index).getValue(badArgs.get(index));
        } else { return null; }
    }
    public Float getFloat(int index) {
        if (valid && getSize() > index && limiters.size() > index) {
            return (Float) limiters.get(index).getValue(badArgs.get(index));
        } else { return null; }
    }
    public Boolean getBoolean(int index) {
        if (valid && getSize() > index && limiters.size() > index) {
            return (Boolean) limiters.get(index).getValue(badArgs.get(index));
        } else { return null; }
    }

    public boolean isValid() {
        return valid;
    }
    public int getSize() {
        return (badArgs != null) ? badArgs.size() : 0;
    }
}
