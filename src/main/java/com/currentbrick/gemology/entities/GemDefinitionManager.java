package com.currentbrick.gemology.entities;

import net.minecraft.resources.Identifier;

import java.util.HashMap;

import java.util.Map;

public class GemDefinitionManager {

    private final Map<Identifier, GemDefinition> definitions = new HashMap<>();

    public void register(GemDefinition definition) {

        definitions.put(definition.getId(), definition);

    }

    public GemDefinition get(Identifier id) {

        return definitions.get(id);

    }

    public void clear() {

        definitions.clear();

    }

}
