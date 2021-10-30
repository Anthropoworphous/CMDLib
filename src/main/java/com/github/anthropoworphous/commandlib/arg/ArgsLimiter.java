package com.github.anthropoworphous.commandlib.arg;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


@SuppressWarnings("unused")
public class ArgsLimiter<T> {
    public ArgsLimiter(@NotNull ArgsType expectedType, List<T> limits, boolean isWhiteList) {
        this.expectedType = expectedType;
        this.limits = limits;
        this.isWhiteList = isWhiteList;
    }
    public ArgsLimiter(@NotNull ArgsType expectedType, List<T> limits) {
        this.expectedType = expectedType;
        this.limits = limits;
        isWhiteList = true;
    }
    public ArgsLimiter(@NotNull ArgsType expectedType) {
        this.expectedType = expectedType;
        this.limits = null;
        isWhiteList = true;
    }

    private final ArgsType expectedType;
    private final List<T> limits;
    private final boolean isWhiteList;
    private T value;

    public ArgsType getExpectedType() {
        return expectedType;
    }

    public List<T> getLimit() {
        return limits;
    }

    public boolean isWhiteList() {
        return isWhiteList;
    }

    @NotNull public T getValue(String input) {
        return value;
    }

    public boolean validate(String input) {
        value = expectedType.convert(input);
        return (input != null
                && value != null
                && (limits == null || limits.contains(value) == isWhiteList));
    }

    public enum ArgsType {
        OBJECT("<Object>",
                "Ex: 1, e, True") {
            @Override
            @Nullable
            @SuppressWarnings("unchecked")
            public Object convert(String input) {
                return input;
            }
        },
        STRING("<String>",
                "Ex: abc, owo, stuff") {
            @Override
            @Nullable
            @SuppressWarnings("unchecked")
            public String convert(String input) {
                return input;
            }
        },
        INT("<Integer>",
                "Ex: 1, 2, -3") {
            @Override
            @Nullable
            @SuppressWarnings("unchecked")
            public Integer convert(String input) {
                int output;

                try {
                    output = Integer.parseInt(input);
                } catch(NumberFormatException e) {
                    return null;
                }

                return output;
            }
        },
        DOUBLE("<Double>",
                "Ex: 1.2, -6.9, 4.20") {
            @Override
            @Nullable
            @SuppressWarnings("unchecked")
            public Double convert(String input) {
                double output;

                try {
                    output = Double.parseDouble(input);
                } catch(NumberFormatException e) {
                    return null;
                }

                return output;
            }
        },
        FLOAT("<Float>",
                "Ex: 1.2, -6.9, 4.20") {
            @Override
            @Nullable
            @SuppressWarnings("unchecked")
            public Float convert(String input) {
                float output;

                try {
                    output = Float.parseFloat(input);
                } catch(NumberFormatException e) {
                    return null;
                }

                return output;
            }
        },
        LONG("<Long>",
                "Ex: 1, 5, 9,223,372,036,854,775,807") {
            @Override
            @Nullable
            @SuppressWarnings("unchecked")
            public Long convert(String input) {
                long output;

                try {
                    output = Long.parseLong(input);
                } catch(NumberFormatException e) {
                    return null;
                }

                return output;
            }
        },
        BOOLEAN("<Boolean>",
                "True/False") {
            @Override
            @Nullable
            @SuppressWarnings("unchecked")
            public Boolean convert(String input) {
                boolean output;

                try {
                    output = Boolean.parseBoolean(input);
                } catch(NumberFormatException e) {
                    return null;
                }

                return output;
            }
        };

        ArgsType(String getHumanReadableName, String getHumanReadableDescription) {
            this.getHumanReadableName = getHumanReadableName;
            this.getHumanReadableDescription = getHumanReadableDescription;
        }

        private final String getHumanReadableName;
        private final String getHumanReadableDescription;

        public String getHumanReadableName() {
            return getHumanReadableName;
        }
        public String getgetHumanReadableDescription() {
            return getHumanReadableDescription;
        }

        @Nullable public abstract <T> T convert(String input);
    }
}
