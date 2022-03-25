package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.block.Biome;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BiomeVar extends LoneTypes<Biome> {
    public BiomeVar() {
        super("<Biome>", "desert, ocean, swamp");
    }

    @Override

    public Optional<Biome> stringToArgType(String input) {
        return Stream.of(Biome.values())
                .filter(b -> b.name().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public String argTypeToString(Biome input) {
        return input.name();
    }

    @Override

    public IArgParser<Biome> parser() {
        return new ArgParser<>(this,
                () -> Stream.of(Biome.values())
                        .map(biome -> biome.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
