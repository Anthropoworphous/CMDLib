package com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.parser.IArgParser;
import com.github.anthropoworphous.cmdlib.arg.parser.implementation.ArgParser;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.LoneTypes;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectionVar extends LoneTypes<BlockFace> {
    public DirectionVar() {
        super("<Direction>", "north, up, south");
    }

    @Override
    @NotNull
    public Optional<BlockFace> stringToArgType(String input) {
        return Stream.of(BlockFace.values())
                .filter(face -> face.name().equalsIgnoreCase(input))
                .findAny();
    }

    @Override
    public @NotNull String argTypeToString(BlockFace input) {
        return input.name();
    }

    @Override
    @NotNull
    public IArgParser<BlockFace> parser() {
        return new ArgParser<>(this,
                () -> Stream.of(BlockFace.values())
                        .map(face -> face.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
