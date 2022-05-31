package com.github.anthropoworphous.cmdlib.command.variable.multi;

import com.github.anthropoworphous.cmdlib.command.variable.MultiVar;
import com.github.anthropoworphous.cmdlib.command.variable.single.FloatVar;
import org.bukkit.Location;

import java.util.function.Supplier;

public class FramedLocationVar extends MultiVar.TriVar<Location, LocationVar, FloatVar, FloatVar> {
    @Override
    protected Location convert(Input input, LocationVar var1, FloatVar var2, FloatVar var3) {
        Location loc = var1.get(input);
        loc.setYaw(var2.get(input));
        loc.setPitch(var3.get(input));
        return loc;
    }

    @Override
    protected Supplier<LocationVar> getVar1() {
        return LocationVar::new;
    }

    @Override
    protected Supplier<FloatVar> getVar2() {
        return FloatVar::new;
    }

    @Override
    protected Supplier<FloatVar> getVar3() {
        return FloatVar::new;
    }
}
