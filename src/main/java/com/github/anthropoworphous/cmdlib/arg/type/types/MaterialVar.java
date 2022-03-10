package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MaterialVar extends BaseTypes<MaterialVar, Material> {
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
    public ArgParser<MaterialVar, Material> parser() {
        return new BaseArgParser<>(this,
                () -> Stream.of(Material.values())
                        .map(face -> face.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
