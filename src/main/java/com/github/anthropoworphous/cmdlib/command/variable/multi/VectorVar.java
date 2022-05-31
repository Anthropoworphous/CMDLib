package com.github.anthropoworphous.cmdlib.command.variable.multi;

import com.github.anthropoworphous.cmdlib.command.variable.MultiVar;
import com.github.anthropoworphous.cmdlib.command.variable.single.DoubleVar;
import org.bukkit.util.Vector;

import java.util.function.Supplier;

public class VectorVar extends MultiVar.TriVar<Vector, DoubleVar, DoubleVar, DoubleVar> {
    @Override
    protected Vector convert(Input input, DoubleVar var1, DoubleVar var2, DoubleVar var3) {
        return new Vector(
                var1.get(input),
                var2.get(input),
                var3.get(input)
        );
    }

    @Override
    protected Supplier<DoubleVar> getVar1() {
        return DoubleVar::new;
    }

    @Override
    protected Supplier<DoubleVar> getVar2() {
        return DoubleVar::new;
    }

    @Override
    protected Supplier<DoubleVar> getVar3() {
        return DoubleVar::new;
    }
}
