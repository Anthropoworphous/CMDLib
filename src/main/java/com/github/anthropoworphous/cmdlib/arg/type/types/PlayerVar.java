package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerVar extends BaseTypes<Player> {
    public PlayerVar() {
        super("<Player>", "Anthropoworphous, or some other player's name");
    }

    @Override
    public @NotNull Optional<Player> stringToArgType(String input) {
        return Optional.ofNullable(Bukkit.getPlayer(input));
    }

    @Override
    public @NotNull String argTypeToString(Player input) {
        return input.getName();
    }

    @Override
    @NotNull
    public ArgParser<Player> parser() {
        return new BaseArgParser<>(this, () -> Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList()));
    }
}
