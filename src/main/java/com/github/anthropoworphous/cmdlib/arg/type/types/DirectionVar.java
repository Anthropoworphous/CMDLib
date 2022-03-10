package com.github.anthropoworphous.cmdlib.arg.type.types;

import com.github.anthropoworphous.cmdlib.arg.type.BaseTypes;
import com.github.anthropoworphous.cmdlib.processor.parser.ArgParser;
import com.github.anthropoworphous.cmdlib.processor.parser.BaseArgParser;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectionVar extends BaseTypes<DirectionVar, BlockFace> {
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
    public ArgParser<DirectionVar, BlockFace> parser() {
        return new BaseArgParser<>(this,
                () -> Stream.of(BlockFace.values())
                        .map(face -> face.name().toLowerCase())
                        .collect(Collectors.toList()));
    }
}
