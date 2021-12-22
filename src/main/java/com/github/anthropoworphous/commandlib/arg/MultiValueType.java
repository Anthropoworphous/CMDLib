package com.github.anthropoworphous.commandlib.arg;

import com.github.anthropoworphous.commandlib.adaptor.CMDLimiter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MultiValueType implements ArgsType {
    VECTOR("<Vector>",
            "3 number like: 69 1 420") {
        /**
         * parse a vector
         * @param input (number number number)
         * @return vector or null if something failed
         */
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Vector stringToArgType(String input) {
            List<Double> coords = Arrays.stream(input.split(" "))
                    .map(str -> (Double) BaseTypes.DOUBLE.stringToArgType(str))
                    .collect(Collectors.toList());
            return new Vector(coords.get(0), coords.get(1), coords.get(2));
        }

        /**
         * parse a vector
         * @param objs 3 doubles
         * @return vector or null if something failed
         */
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Vector assemble(Object... objs) {
            if (objs.length != 3) { return null; }
            List<Double> coords = Arrays.stream(objs)
                    .filter(o -> o instanceof Double)
                    .map(o -> (Double) o)
                    .collect(Collectors.toList());
            if (coords.size() != 3) { return null; }
            return new Vector(coords.get(0), coords.get(1), coords.get(2));
        }

        @NotNull
        @SuppressWarnings("unchecked")
        List<CMDLimiter<?>> constructLimiter() {
            return List.of(
                    BaseTypes.DOUBLE.constructLimiter(), //x
                    BaseTypes.DOUBLE.constructLimiter(), //y
                    BaseTypes.DOUBLE.constructLimiter() //z
            );
        }
    },
    LOCATION_NO_ANGLE("<Location>",
            "world + 3 number like: world_the_end 69 1 420") {
        /**
         * parse a location with out pitch and yaw
         * @param str world number number number
         * @return vector or null if something failed
         */
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Location stringToArgType(String str) {
            List<String> input = List.of(str.split(" "));
            if (input.size() != 4) { return null; }

            World world = Bukkit.getWorld(input.get(0));
            if (world == null) { return null; }

            List<Double> coords = input.subList(1, input.size()).stream()
                    .map(s -> (Double) BaseTypes.DOUBLE.stringToArgType(s))
                    .collect(Collectors.toList());
            if (coords.contains(null)) { return null; }

            return new Location(world, coords.get(0), coords.get(1), coords.get(2));
        }

        /**
         * parse a location with out pitch and yaw
         * @param objs 3 doubles
         * @return vector or null if something failed
         */
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Location assemble(Object... objs) {
            if (objs.length != 4) { return null; }

            List<Object> objList = List.of(objs);
            if (!(objList.get(0) instanceof World world)) { return null;}

            List<Double> coords = objList.subList(1, objList.size()).stream()
                    .filter(o -> o instanceof Double)
                    .map(o -> (Double) o)
                    .collect(Collectors.toList());
            if (coords.size() != 3) { return null; }

            return new Location(world, coords.get(0), coords.get(1), coords.get(2));
        }

        @NotNull
        @SuppressWarnings("unchecked")
        List<CMDLimiter<?>> constructLimiter() {
            return List.of(
                    BaseTypes.WORLD.constructLimiter(), //world
                    BaseTypes.DOUBLE.constructLimiter(), //x
                    BaseTypes.DOUBLE.constructLimiter(), //y
                    BaseTypes.DOUBLE.constructLimiter() //z
            );
        }
    },
    LOCATION_WITH_ANGLE("<Location+Angle>",
            "world + 5 number like: world_the_end 69 1 420 6 9") {
        /**
         * parse a vector
         * @param str world number number number
         * @return vector or null if something failed
         */
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Location stringToArgType(String str) {
            List<String> input = List.of(str.split(" "));
            if (input.size() != 6) { return null; }

            World world = Bukkit.getWorld(input.get(0));
            if (world == null) { return null; }

            List<Double> doubles = input.subList(1, input.size()).stream()
                    .map(s -> (Double) BaseTypes.DOUBLE.stringToArgType(s))
                    .collect(Collectors.toList());
            if (doubles.contains(null)) { return null; }

            return new Location(world, doubles.get(0), doubles.get(1), doubles.get(2),
                    doubles.get(3).floatValue(), doubles.get(4).floatValue());
        }

        /**
         * parse a location
         * @param objs 3 doubles
         * @return vector or null if something failed
         */
        @Override
        @Nullable
        @SuppressWarnings("unchecked")
        public Location assemble(Object... objs) {
            if (objs.length != 6) { return null; }

            List<Object> objList = List.of(objs);
            if (!(objList.get(0) instanceof World world)) { return null;}

            List<Double> doubles = objList.subList(1, objList.size()).stream()
                    .filter(o -> o instanceof Double)
                    .map(o -> (Double) o)
                    .collect(Collectors.toList());
            if (doubles.size() != 5) { return null; }

            return new Location(world, doubles.get(0), doubles.get(1), doubles.get(2),
                    doubles.get(3).floatValue(), doubles.get(4).floatValue());
        }

        @NotNull
        @SuppressWarnings("unchecked")
        List<CMDLimiter<?>> constructLimiter() {
            return List.of(
                    BaseTypes.WORLD.constructLimiter(), //world
                    BaseTypes.DOUBLE.constructLimiter(), //x
                    BaseTypes.DOUBLE.constructLimiter(), //y
                    BaseTypes.DOUBLE.constructLimiter(), //z
                    BaseTypes.DOUBLE.constructLimiter(), //yaw
                    BaseTypes.DOUBLE.constructLimiter() //pitch
            );
        }
    };

    MultiValueType(String readableName, String readableDescription) {
        this.readableName = readableName;
        this.readableDescription = readableDescription;
    }

    private final String readableName;
    private final String readableDescription;

    @Nullable
    public abstract <T> T assemble(Object... objs);

    @NotNull abstract <T> List<CMDLimiter<T>> constructLimiter();

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
