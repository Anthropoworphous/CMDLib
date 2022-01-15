package com.github.anthropoworphous.commandlib.arg;

import com.github.anthropoworphous.commandlib.CMDLib;
import com.github.anthropoworphous.commandlib.adaptor.CMDLimiter;
import main.structure.tree.Connected;
import main.structure.tree.IConnectable;

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

        if (CMDLib.logDetails()) {
            CMDLib.log("Checking argument" + ((currentInputs.length == 1) ? " " : "s ")
                    + "for autocomplete"
            );
        }

        badArgs = List.of(currentInputs);
        if (limiters != null && limiters.size() != 0 && limiters.stream().allMatch(l -> l.getChild() != null)) {
            List<Function<IConnectable, Boolean>> filters = new ArrayList<>();
            if (badArgs.size() > 1) {
                badArgs.subList(0, badArgs.size()-1)
                        .forEach(arg -> filters.add(connectable -> ((CMDLimiter<?>) connectable).validate(arg)));
            }

            List<Connected> filtered = new ArrayList<>();
            List<Connected> filtered_ish;
            for (Connected connected : limiters) {
                if (badArgs.size() > 1) {
                    filtered_ish = connected.multiLayerFilter(filters, false);

                    if (CMDLib.logDetails()) {
                        CMDLib.log("-\tFiltering route: " + connected);
                    }

                    for (Connected f : filtered_ish) {
                        if (f.isChildless()) {
                            if (CMDLib.logDetails()) {
                                CMDLib.log("-\tRoute removed for it has no following argument for completion: "
                                        + f);
                            }
                        } else {
                            filtered.addAll(Objects.requireNonNull(f.getChild()));
                        }
                    }
                } else {
                    filtered.addAll(Objects.requireNonNull(connected.getChild()));

                    if (CMDLib.logDetails()) {
                        CMDLib.log("-\tToo short for filtering, added child of: " + connected);
                    }
                }
            }

            for (Connected connected : filtered) {
                CMDLimiter<?> fillWith = (CMDLimiter<?>) connected.getValue();

                if (fillWith != null) {
                    if (fillWith.getAutoFill().size() != 0) {
                        if (CMDLib.logDetails()) {
                            CMDLib.log("-\tAutoFill found, using it for AutoComplete");
                        }

                        autoFill.addAll(fillWith.getAutoFill());
                    } else if (fillWith.getLimit().size() > 0) {
                        if (CMDLib.logDetails()) {
                            CMDLib.log("-\tLimits found, using it for AutoComplete");
                        }

                        autoFill.addAll(fillWith.getLimit()
                                .stream()
                                .map(limit -> fillWith.getExpectedType().argTypeToString(limit))
                                .collect(Collectors.toList()));
                    } else {
                        if (CMDLib.logDetails()) {
                            CMDLib.log("-\tCould not find anything for autofill, using type name instead");
                        }

                        autoFill.add(fillWith.getExpectedType().getReadableName());
                    }
                } else {
                    if (CMDLib.logDetails()) {
                        CMDLib.log("-\tType not found...how?");
                    }

                    autoFill.add("<Unknown>");
                }
            }
            if (badArgs.size() > 0) {
                if (CMDLib.logDetails()) {
                    CMDLib.log("-Enough argument for final incomplete argument filter, filtering");
                }

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

        if (CMDLib.logDetails()) {
            CMDLib.log("Checking argument" + ((reallyBadArgs.length == 1) ? " " : "s ")
                    + "for execution"
            );
        }

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
            List<Function<IConnectable, Boolean>> filters = new ArrayList<>();
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
    public BaseTypes getType(int index) {
        return limiter.get(index).getExpectedType();
    }

    public <T> T get(Class<T> type, int index) {
        return limiter.get(index).getExpectedType().stringToArgType(badArgs.get(index));
    }

    /**
     * This is just getObject(), there is no difference
     * @param index is just index
     * @return object of this index
     */
    public Object get(int index) {
        return getObject(index);
    }

    /**
     * This return object
     * If there was no limiter it'll just return the string in object form
     * @param index is just index
     * @return object of this index
     */
    public Object getObject(int index) {
        return (limiter.get(index) == null) ?
                badArgs.get(index) :
                limiter.get(index).getValue(badArgs.get(index));
    }
    public String getString(int index) {
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