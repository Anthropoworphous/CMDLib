package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.attribute.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AttributeVar extends BaseTypes<Attribute> {
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
    public ArgParser<Attribute> parser() {
        return new BaseArgParser<>(this,
                () -> Stream.of(Attribute.values())
                        .map(attribute -> attribute.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
