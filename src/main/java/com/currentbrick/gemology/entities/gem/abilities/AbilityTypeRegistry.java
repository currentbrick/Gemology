package com.currentbrick.gemology.entities.gem.abilities;

import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AbilityTypeRegistry {

    private static final Map<Identifier, Supplier<Ability>> TYPES = new HashMap<>();

    public static void register(Identifier id, Supplier<Ability> supplier) {
        TYPES.put(id, supplier);
    }

    public static Ability create(Identifier id) {
        Supplier<Ability> supplier = TYPES.get(id);

        if (supplier == null) {
            return null;
        }

        return supplier.get();
    }
}