package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;

public class WorldVar extends LoneTypes<World> {
    public WorldVar() {
        super("<World>", "world_the_end, or some other world's name");
    }

    @Override
    @NotNull
    public Optional<World> stringToArgType(String input) {
        return Optional.ofNullable(Bukkit.getWorld(input));
    }

    @Override
    public @NotNull String argTypeToString(World input) {
        return input.getName();
    }

    @Override
    @NotNull
    public IArgParser<World> parser() {
        return new ArgParser<>(this,
                () -> (Bukkit.getWorlds().stream()
                        .map(World::getName)
                        .collect(Collectors.toList())));
    }
}