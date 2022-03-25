package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerVar extends LoneTypes<Player> {
    public PlayerVar() {
        super("<Player>", "Anthropoworphous, or some other player's name");
    }

    @Override
    public Optional<Player> stringToArgType(String input) {
        return Optional.ofNullable(Bukkit.getPlayer(input));
    }

    @Override
    public String argTypeToString(Player input) {
        return input.getName();
    }

    @Override

    public IArgParser<Player> parser() {
        return new ArgParser<>(this, () -> Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList()));
    }
}
