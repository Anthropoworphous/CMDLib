package com.github.anthropoworphous.commandlib.arg;

import com.github.anthropoworphous.commandlib.adaptor.CMDLimiter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum BaseTypes implements ArgsType {
    OBJECT("<Object>",
            "Ex: 1, e, True") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Object stringToArgType(String input) {
            return input;
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Object> constructLimiter() {
            return new CMDLimiter<>(this);
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

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<String> constructLimiter() {
            return new CMDLimiter<>(this);
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

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Integer> constructLimiter() {
            return new CMDLimiter<>(this);
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

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Double> constructLimiter() {
            return new CMDLimiter<>(this);
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

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Float> constructLimiter() {
            return new CMDLimiter<>(this);
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

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Long> constructLimiter() {
            return new CMDLimiter<>(this);
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

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Boolean> constructLimiter() {
            return new CMDLimiter<Boolean>(this)
                    .addAutoFill(() -> List.of("true", "false"));
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

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Player> constructLimiter() {
            return new CMDLimiter<Player>(this)
                    .addAutoFill(() -> Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.toList()));
        }
    },
    WORLD("<World>",
            "world_the_end, or some other world's name") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public World stringToArgType(String input) {
            return Bukkit.getWorld(input);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((World) input).getName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<World> constructLimiter() {
            return new CMDLimiter<World>(this)
                    .addAutoFill(() -> (Bukkit.getWorlds().stream()
                            .map(World::getName)
                            .collect(Collectors.toList())));
        }
    },
    TIME("<Time>",
            "420s, 69day, support t/s/m/h/d/week/month/year") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Double stringToArgType(String input) {
            Matcher match = Pattern.compile("([0-9])(\\w+)").matcher(input);
            if (!match.find()) {
                return null;
            }

            Double rawTime = INT.stringToArgType(match.group(0));
            if (rawTime == null) {
                return null;
            }

            return switch (match.group(1).toLowerCase()) {
                case "ms", "millis", "millisecond", "milliseconds" ->
                        rawTime / Bukkit.getServer().getAverageTickTime() / 20;
                case "t", "tick", "ticks" ->
                        rawTime / 20;
                case "s", "sec", "second", "seconds" ->
                        rawTime;
                case "m", "min", "minute", "minutes" ->
                        rawTime * 60;
                case "h", "hr", "hour", "hours" ->
                        rawTime * 60 * 60;
                case "d", "day", "days" ->
                        rawTime * 60 * 60 * 24;
                case "w", "week", "weeks" ->
                        rawTime * 60 * 60 * 24 * 7;
                case "mon", "month", "months" ->
                        rawTime * 60 * 60 * 24 * 7 * 30;
                case "y", "year", "years" ->
                        rawTime * 60 * 60 * 24 * 7 * 30 * 12;
                default -> null;
            };
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            double seconds = ((Double) input);
            String[] unit = { "ms", "t", "s", "m", "h", "days", "weeks", "months", "years" };
            double[] unitLength = { 1/Bukkit.getAverageTickTime(), (double)(1/20), 1, 60, 60*60, 60*60*24, 60*60*24*7,
                    60*60*24*7*30, 60*60*24*7*30*12};
            for (int i = 0; i < unit.length; i++) {
                if (seconds / unitLength[i] <= 1000) {
                    return Math.round(seconds/unitLength[i] * 100)/100.0 + unit[i];
                }
            }
            return Math.round(seconds/unitLength[unit.length-1] * 100)/100.0 + unit[unit.length-1];
        }

        /**
         * time unit is second
         * @return the limiter
         */
        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Double> constructLimiter() {
            return new CMDLimiter<>(this);
        }
    },
    ATTRIBUTE("<Attribute>",
            "generic_attack_speed, generic_armor, generic_movement_speed") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Attribute stringToArgType(String input) {
            return Stream.of(Attribute.values())
                    .filter(att -> att.name().equalsIgnoreCase(input))
                    .findAny()
                    .orElse(null);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((Attribute) input).name();
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Attribute> constructLimiter() {
            return new CMDLimiter<Attribute>(this)
                    .addAutoFill(() -> Stream.of(Attribute.values())
                            .map(attribute -> attribute.name().toLowerCase())
                            .collect(Collectors.toList()));
        }
    },
    BIOME("<Biome>",
                      "desert, ocean, swamp") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Biome stringToArgType(String input) {
            return Stream.of(Biome.values())
                    .filter(b -> b.name().equalsIgnoreCase(input))
                    .findAny()
                    .orElse(null);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((Biome) input).name();
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<Biome> constructLimiter() {
            return new CMDLimiter<Biome>(this)
                    .addAutoFill(() -> Stream.of(Biome.values())
                            .map(biome -> biome.name().toLowerCase())
                            .collect(Collectors.toList()));
        }
    },
    DIRECTION("<Direction>",
            "north, up, south") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public BlockFace stringToArgType(String input) {
            return Stream.of(BlockFace.values())
                    .filter(face -> face.name().equalsIgnoreCase(input))
                    .findAny()
                    .orElse(null);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((BlockFace) input).name();
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull CMDLimiter<BlockFace> constructLimiter() {
            return new CMDLimiter<BlockFace>(this)
                    .addAutoFill(() -> Stream.of(BlockFace.values())
                            .map(face -> face.name().toLowerCase())
                            .collect(Collectors.toList()));
        }
    };

    BaseTypes(String readableName, String readableDescription) {
        this.readableName = readableName;
        this.readableDescription = readableDescription;
    }

    private final String readableName;
    private final String readableDescription;

    @NotNull abstract <T> CMDLimiter<T> constructLimiter();

    @Override
    public String getReadableName() {
        return readableName;
    }
    @Override
    public String getReadableDescription() {
        return readableDescription;
    }

    @Override
    @NotNull public String argTypeToString(Object input) { return String.valueOf(input); }
}
