package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BiomeVar extends BaseTypes<BiomeVar, Biome> {
    public BiomeVar() {
        super("<Biome>", "desert, ocean, swamp");
    }

    @Override
    @NotNull
    public Optional<Biome> stringToArgType(String input) {
        return Stream.of(Biome.values())
                .filter(b -> b.name().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public @NotNull String argTypeToString(Biome input) {
        return input.name();
    }

    @Override
    @NotNull
    public ArgParser<BiomeVar, Biome> parser() {
        return new BaseArgParser<>(this,
                () -> Stream.of(Biome.values())
                        .map(biome -> biome.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
