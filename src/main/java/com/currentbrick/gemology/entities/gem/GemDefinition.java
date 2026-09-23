package com.currentbrick.gemology.entities.gem;

import net.minecraft.resources.Identifier;

import java.util.List;

public class GemDefinition {

    private final Identifier id;
    private final GemStats stats;
    private final GemDimensions dimensions;
    private final List<Identifier> abilities;

    public GemDefinition(Identifier id, GemStats stats, GemDimensions dimensions, List<Identifier> abilities) {
        this.id = id;
        this.stats = stats;
        this.dimensions = dimensions;
        this.abilities = abilities;
    }

    public Identifier getId() {
        return id;
    }

    public GemDimensions getDimensions() {
        return dimensions;
    }

    public GemStats getStats() {
        return stats;
    }

    public List<Identifier> getAbilities() {
        return abilities;
    }
}