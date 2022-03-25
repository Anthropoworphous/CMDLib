package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaterialVar extends LoneTypes<Material> {
    public MaterialVar() {
        super("<Material>", "");
    }

    @Override
    @NotNull
    public Optional<Material> stringToArgType(String input) {
        return Stream.of(Material.values())
                .filter(material -> material.name().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public @NotNull String argTypeToString(Material input) {
        return input.name();
    }

    @Override
    @NotNull
    public IArgParser<Material> parser() {
        return new ArgParser<>(this,
                () -> Stream.of(Material.values())
                        .map(face -> face.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
