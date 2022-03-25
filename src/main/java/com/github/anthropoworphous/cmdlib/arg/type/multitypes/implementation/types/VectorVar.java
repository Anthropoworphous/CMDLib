package com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation.types;

import com.github.anthropoworphous.cmdlib.arg.type.ArgType;
import com.github.anthropoworphous.cmdlib.arg.type.lonetypes.implementation.types.DoubleVar;
import com.github.anthropoworphous.cmdlib.arg.type.multitypes.implementation.MultiValueType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class VectorVar extends MultiValueType<Vector> {
    public VectorVar() {
        super("<Vector>", "Just 3 number, EX: 1 1 1");
    }

    @Override
    public @NotNull Optional<Vector> combineInto(List<Object> input) {
        return Optional.of(
                new Vector(
                        (Double) input.get(0),
                        (Double) input.get(1),
                        (Double) input.get(2)
                )
        );
    }

    @Override
    public List<ArgType<?>> divideInto() {
        return List.of(
                new DoubleVar(),
                new DoubleVar(),
                new DoubleVar()
        );
    }
}
