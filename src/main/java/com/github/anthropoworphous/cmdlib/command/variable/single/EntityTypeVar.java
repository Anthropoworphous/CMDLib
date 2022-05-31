package com.github.anthropoworphous.cmdlib.command.variable.single;

import com.github.anthropoworphous.cmdlib.command.variable.Var;
import org.bukkit.entity.EntityType;

import java.util.List;

public class EntityTypeVar extends Var<EntityType> {
    @Override
    public EntityType convert(List<String> input) throws IllegalArgumentException {
        try {
            return EntityType.valueOf(input.get(0));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
