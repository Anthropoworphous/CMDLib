package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.event.inventory.InventoryType;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InventoryTypeVar extends LoneTypes<InventoryType> {
    public InventoryTypeVar() {
        super("<Inventory>", "");
    }

    @Override

    public Optional<InventoryType> stringToArgType(String input) {
        return Stream.of(InventoryType.values())
                .filter(inv -> inv.name().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public String argTypeToString(InventoryType input) {
        return input.name();
    }

    @Override

    public IArgParser<InventoryType> parser() {
        return new ArgParser<>(this,
                () -> Stream.of(InventoryType.values())
                        .map(face -> face.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
