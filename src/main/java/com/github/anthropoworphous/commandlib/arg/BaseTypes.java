package com.github.anthropoworphous.commandlib.arg;

import com.github.anthropoworphous.commandlib.adaptor.CMDLimiter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.potion.PotionEffectType;
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
        @NotNull
        public CMDLimiter<Object> constructLimiter() {
            return new CMDLimiter<>(this);
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Object.class;
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
        @NotNull
        public CMDLimiter<String> constructLimiter() {
            return new CMDLimiter<>(this);
        }

        @Override
        public @NotNull Class<?> returnType() {
            return String.class;
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
        @NotNull
        public CMDLimiter<Integer> constructLimiter() {
            return new CMDLimiter<>(this);
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Integer.class;
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
        @NotNull
        public CMDLimiter<Double> constructLimiter() {
            return new CMDLimiter<>(this);
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Double.class;
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
        @NotNull
        public CMDLimiter<Float> constructLimiter() {
            return new CMDLimiter<>(this);
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Float.class;
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
        @NotNull
        public CMDLimiter<Long> constructLimiter() {
            return new CMDLimiter<>(this);
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Long.class;
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
        @NotNull
        public CMDLimiter<Boolean> constructLimiter() {
            return new CMDLimiter<Boolean>(this)
                    .addAutoFill(() -> List.of("true", "false"));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Boolean.class;
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
        @NotNull
        public CMDLimiter<Player> constructLimiter() {
            return new CMDLimiter<Player>(this)
                    .addAutoFill(() -> Bukkit.getOnlinePlayers().stream()
                            .map(Player::getName)
                            .collect(Collectors.toList()));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Player.class;
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
        @NotNull
        public CMDLimiter<World> constructLimiter() {
            return new CMDLimiter<World>(this)
                    .addAutoFill(() -> (Bukkit.getWorlds().stream()
                            .map(World::getName)
                            .collect(Collectors.toList())));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return World.class;
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
        @NotNull
        public CMDLimiter<Double> constructLimiter() {
            return new CMDLimiter<>(this);
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Double.class;
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
        @NotNull
        public CMDLimiter<Attribute> constructLimiter() {
            return new CMDLimiter<Attribute>(this)
                    .addAutoFill(() -> Stream.of(Attribute.values())
                            .map(attribute -> attribute.name().toLowerCase())
                            .collect(Collectors.toList()));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Attribute.class;
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
        @NotNull
        public CMDLimiter<Biome> constructLimiter() {
            return new CMDLimiter<Biome>(this)
                    .addAutoFill(() -> Stream.of(Biome.values())
                            .map(biome -> biome.name().toLowerCase())
                            .collect(Collectors.toList()));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Biome.class;
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
        @NotNull
        public CMDLimiter<BlockFace> constructLimiter() {
            return new CMDLimiter<BlockFace>(this)
                    .addAutoFill(() -> Stream.of(BlockFace.values())
                            .map(face -> face.name().toLowerCase())
                            .collect(Collectors.toList()));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return BlockFace.class;
        }
    },
    MATERIAL("<Material>",
            "") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Material stringToArgType(String input) {
            return Stream.of(Material.values())
                    .filter(material -> material.name().equalsIgnoreCase(input))
                    .findAny()
                    .orElse(null);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((Material) input).name();
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull
        public CMDLimiter<Material> constructLimiter() {
            return new CMDLimiter<Material>(this)
                    .addAutoFill(() -> Stream.of(Material.values())
                            .map(face -> face.name().toLowerCase())
                            .collect(Collectors.toList()));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return Material.class;
        }
    },
    INVENTORY("<Inventory>",
            "") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public InventoryType stringToArgType(String input) {
            return Stream.of(InventoryType.values())
                    .filter(inv -> inv.name().equalsIgnoreCase(input))
                    .findAny()
                    .orElse(null);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((InventoryType) input).name();
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull
        public CMDLimiter<InventoryType> constructLimiter() {
            return new CMDLimiter<InventoryType>(this)
                    .addAutoFill(() -> Stream.of(InventoryType.values())
                            .map(face -> face.name().toLowerCase())
                            .collect(Collectors.toList()));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return InventoryType.class;
        }
    },
    POTION_EFFECT_TYPE("<Potion effect type>",
            "") {
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public PotionEffectType stringToArgType(String input) {
            return Stream.of(PotionEffectType.values())
                    .filter(eff -> eff.getName().equalsIgnoreCase(input))
                    .findAny()
                    .orElse(null);
        }

        @Override
        public @NotNull String argTypeToString(Object input) {
            return ((PotionEffectType) input).getName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @NotNull
        public CMDLimiter<PotionEffectType> constructLimiter() {
            return new CMDLimiter<PotionEffectType>(this)
                    .addAutoFill(() -> Stream.of(PotionEffectType.values())
                            .map(face -> face.getName().toLowerCase())
                            .collect(Collectors.toList()));
        }

        @Override
        public @NotNull Class<?> returnType() {
            return InventoryType.class;
        }
    };

    BaseTypes(String readableName, String readableDescription) {
        this.readableName = readableName;
        this.readableDescription = readableDescription;
    }

    private final String readableName;
    private final String readableDescription;

    @NotNull
    public abstract <T> CMDLimiter<T> constructLimiter();

    @Override
    public String getReadableName() {
        return readableName;
    }
    @Override
    public String getReadableDescription() {
        return readableDescription;
    }

    @Override
    @NotNull
    public String argTypeToString(Object input) { return String.valueOf(input); }
}
