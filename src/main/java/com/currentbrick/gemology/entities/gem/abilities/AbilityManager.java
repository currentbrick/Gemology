package com.currentbrick.gemology.entities.gem.abilities;

import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class AbilityManager {

    private final Map<Identifier, AbilityDefinition> definitions = new HashMap<>();

    public void register(AbilityDefinition definition) {
        definitions.put(definition.getId(), definition);
    }

    public AbilityDefinition get(Identifier id) {
        return definitions.get(id);
    }

    public void clear() {
        definitions.clear();
    }

}