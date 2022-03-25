package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttributeVar extends LoneTypes<Attribute> {
    public AttributeVar() {
        super("<Attribute>", "generic_attack_speed, generic_armor, generic_movement_speed");
    }

    @Override
    @NotNull
    public Optional<Attribute> stringToArgType(String input) {
        return Stream.of(Attribute.values())
                .filter(att -> att.name().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public @NotNull String argTypeToString(Attribute input) {
        return input.name();
    }

    @Override
    @NotNull
    public IArgParser<Attribute> parser() {
        return new ArgParser<>(this,
                () -> Stream.of(Attribute.values())
                        .map(attribute -> attribute.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
