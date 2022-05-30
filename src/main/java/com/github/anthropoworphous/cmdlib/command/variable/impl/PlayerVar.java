package com.github.anthropoworphous.cmdlib.command.variable.impl;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class PlayerVar extends Var<Player> {
    @Override
    public Player convert(List<String> input) throws IllegalArgumentException {
        return Optional.ofNullable(Bukkit.getPlayerExact(input.get(0))).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<String> autoComplete() {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).toList();
    }
}
