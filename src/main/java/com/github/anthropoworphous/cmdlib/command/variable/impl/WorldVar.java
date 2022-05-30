package com.github.anthropoworphous.cmdlib.command.variable.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;
import java.util.Optional;

public class WorldVar extends Var<World> {
    @Override
    public World convert(List<String> input) throws IllegalArgumentException {
        return Optional.ofNullable(Bukkit.getWorld(input.get(0))).orElseThrow(IllegalArgumentException::new);
    }
}
