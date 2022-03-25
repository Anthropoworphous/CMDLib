package com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types.DoubleVar;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types.WorldVar;
import com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation.MultiValueType;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class LocationVar extends MultiValueType<Location> {
    public LocationVar() {
        super("<Location>", "This describe a position in the server");
    }

    @Override
    public @NotNull Optional<Location> combineInto(List<Object> input) {
        return Optional.of(
                new Location(
                        (World) input.get(0),
                        (double) input.get(1),
                        (double) input.get(2),
                        (double) input.get(3)
                )
        );
    }

    @Override
    public List<ArgType<?>> divideInto() {
        return List.of(
                new WorldVar(),
                new DoubleVar(),
                new DoubleVar(),
                new DoubleVar()
        );
    }
}
