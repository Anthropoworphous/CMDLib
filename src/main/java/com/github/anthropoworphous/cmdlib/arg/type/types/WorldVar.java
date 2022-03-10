package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;

public class WorldVar extends BaseTypes<WorldVar, World> {
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
    public ArgParser<WorldVar, World> parser() {
        return new BaseArgParser<>(this,
                () -> (Bukkit.getWorlds().stream()
                        .map(World::getName)
                        .collect(Collectors.toList())));
    }
}
