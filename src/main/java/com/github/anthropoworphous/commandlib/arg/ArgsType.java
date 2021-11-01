package com.github.anthropoworphous.commandlib.arg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum ArgsType {
    OBJECT("<Object>",
            "Ex: 1, e, True") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Object stringToArgType(String input) {
            return input;
        }
    },
    STRING("<String>",
            "Ex: abc, owo, stuff") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public String stringToArgType(String input) {
            return input;
        }
    },
    INT("<Integer>",
            "Ex: 1, 2, -3") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Integer stringToArgType(String input) {
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
        public Double stringToArgType(String input) {
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
        public Float stringToArgType(String input) {
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
        public Long stringToArgType(String input) {
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
        public Boolean stringToArgType(String input) {
            boolean output;

            try {
                output = Boolean.parseBoolean(input);
            } catch(NumberFormatException e) {
                return null;
            }

            return output;
        }
    },
    PLAYER("<Player>",
            "Anthropoworphous, or some other player's name") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Player stringToArgType(String input) {
            return Bukkit.getPlayer(input);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((Player) input).getName();
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

    @Nullable public abstract <T> T stringToArgType(String input);
    @NotNull public String argTypeToString(Object input) { return String.valueOf(input); }
}
