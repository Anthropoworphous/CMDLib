package com.github.anthropoworphous.cmdlib.command.variable.multi;

import com.github.anthropoworphous.cmdlib.command.variable.single.DoubleVar;
import com.github.anthropoworphous.cmdlib.command.variable.single.WorldVar;
import org.bukkit.Location;

import java.util.function.Supplier;

public class LocationVar extends MultiVar.QuadVar<Location, WorldVar, DoubleVar, DoubleVar, DoubleVar> {
    @Override
    protected Location convert(Input input, WorldVar var1, DoubleVar var2, DoubleVar var3, DoubleVar var4) {
        return new Location(
                var1.get(input),
                var2.get(input),
                var3.get(input),
                var4.get(input)
        );
    }

    @Override
    protected Supplier<WorldVar> getVar1() {
        return WorldVar::new;
    }

    @Override
    protected Supplier<DoubleVar> getVar2() {
        return DoubleVar::new;
    }

    @Override
    protected Supplier<DoubleVar> getVar3() {
        return DoubleVar::new;
    }

    @Override
    protected Supplier<DoubleVar> getVar4() {
        return DoubleVar::new;
    }
}
