package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InventoryTypeVar extends BaseTypes<InventoryTypeVar, InventoryType> {
    public InventoryTypeVar() {
        super("<Inventory>", "");
    }

    @Override
    @NotNull
    public Optional<InventoryType> stringToArgType(String input) {
        return Stream.of(InventoryType.values())
                .filter(inv -> inv.name().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public @NotNull String argTypeToString(InventoryType input) {
        return input.name();
    }

    @Override
    @NotNull
    public ArgParser<InventoryTypeVar, InventoryType> parser() {
        return new BaseArgParser<>(this,
                () -> Stream.of(InventoryType.values())
                        .map(face -> face.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
