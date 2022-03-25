package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PotionEffectTypeVar extends LoneTypes<PotionEffectType> {
    public PotionEffectTypeVar() {
        super("<Potion effect type>", "");
    }

    @Override
    @NotNull
    public Optional<PotionEffectType> stringToArgType(String input) {
        return Stream.of(PotionEffectType.values())
                .filter(eff -> eff.getName().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public @NotNull String argTypeToString(PotionEffectType input) {
        return input.getName();
    }

    @Override
    @NotNull
    public IArgParser<PotionEffectType> parser() {
        return new ArgParser<>(this, () -> Stream.of(PotionEffectType.values())
                .map(face -> face.getName().toLowerCase())
                .collect(Collectors.toList()));
    }
}
