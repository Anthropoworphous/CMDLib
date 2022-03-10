package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PotionEffectTypeVar extends BaseTypes<PotionEffectTypeVar, PotionEffectType> {
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
    public ArgParser<PotionEffectTypeVar, PotionEffectType> parser() {
        return new BaseArgParser<>(this, () -> Stream.of(PotionEffectType.values())
                .map(face -> face.getName().toLowerCase())
                .collect(Collectors.toList()));
    }
}
