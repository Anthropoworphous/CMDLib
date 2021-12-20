package com.github.anthropoworphous.commandlib.arg;

import com.github.anthropoworphous.commandlib.adaptor.CMDLimiter;
import main.structure.tree.Connected;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Need to be optimised...
 */
@SuppressWarnings({"unused"})
public class Args {
    /**
     * search for autofill
     * @param limiters command limit
     * @param currentInputs user input
     */
    public Args(List<Connected> limiters, String[] currentInputs) {
        badArgs = List.of(currentInputs);
        if (limiters != null && limiters.size() != 0 && limiters.stream().allMatch(l -> l.getChild() != null)) {
            List<Function<Connected.IConnectable, Boolean>> filters = new ArrayList<>();
            if (badArgs.size() > 1) {
                badArgs.subList(0, badArgs.size()-1)
                        .forEach(arg -> filters.add(connectable -> ((CMDLimiter<?>) connectable).validate(arg)));
            }

            for (Connected connected : ((badArgs.size() > 1) ?
                    limiters.stream()
                            .flatMap(l -> l.multiLayerFilter(filters, false).stream()
                                    .filter(ll -> !ll.isChildless())
                                    .flatMap(ll -> Objects.requireNonNull(ll.getChild()).stream())
                                    .collect(Collectors.toList()).stream())
                            .collect(Collectors.toList()) :
                    limiters.stream()
                            .flatMap(l -> Objects.requireNonNull(l.getChild()).stream())
                            .collect(Collectors.toList()))) {
                CMDLimiter<?> fillWith = (CMDLimiter<?>) connected.getValue();

                if (fillWith != null) {
                    if (fillWith.getAutoFill().size() != 0) {
                        autoFill.addAll(fillWith.getAutoFill());
                    } else if (fillWith.getLimit().size() > 0) {
                        autoFill.addAll(fillWith.getLimit()
                                .stream()
                                .map(limit -> fillWith.getExpectedType().argTypeToString(limit))
                                .collect(Collectors.toList()));
                    } else {
                        autoFill.add(fillWith.getExpectedType().getHumanReadableName());
                    }
                } else {
                    autoFill.add("<Unknown>");
                }
            }
            if (badArgs.size() > 0) {
                autoFill.removeIf(str -> !str.contains(badArgs.get(badArgs.size()-1)));
            }
        }
    }

    /**
     * varify the user input
     * @param reallyBadArgs user input
     * @param limiters command limit
     */
    public Args(String[] reallyBadArgs, List<Connected> limiters) {
        badArgs = List.of(reallyBadArgs);
        //Check for availability
        //no limit = do not accept args
        if ((limiters == null || limiters.size() == 0)) {
            if (badArgs.size() == 0) {
                valid = true;
            } else {
                invalidReason = "Command do not accept arguments";
            }
        } else if (badArgs.size() == 0) {
            invalidReason = "Missing arguments";
        } else { //have limits
            List<Function<Connected.IConnectable, Boolean>> filters = new ArrayList<>();
            //construct route filters
            badArgs.forEach(arg -> filters.add(connectable -> ((CMDLimiter<?>) connectable).validate(arg)));

            List<List<Connected>> routes = new ArrayList<>();
            for (Connected l : limiters) {
                for (Connected connected : l.multiLayerFilter(filters, true)) {
                    List<Connected> oneWideTreeFromChild = Connected.getSlimTreeFromChild(connected);
                    routes.add(oneWideTreeFromChild);
                }
            }
            if (routes.size() == 0) {
                invalidReason = "Invalid input";
            } else if (routes.size() > 1) {
                invalidReason = "Match multiple args routes, contact dev";
            } else {
                for (Connected c : routes.get(0)) {//cast individually
                    limiter.add((CMDLimiter<?>) c.getValue());
                }
                valid = true;
            }
        }
    }

    private final List<String> badArgs;
    private boolean valid = false;
    private String invalidReason;
    private final List<CMDLimiter<?>> limiter = new ArrayList<>();
    private final List<String> autoFill = new ArrayList<>();

    public boolean isValid() {
        return valid;
    }
    public String invalidReason() {
        return invalidReason;
    }

    public List<String> getAutoFill() {
        return autoFill;
    }

    public int getSize() {
        return (badArgs != null) ? badArgs.size() : 0;
    }

    /**
     * Try to avoid this if possible...why would you need this anyway
     */
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