package com.currentbrick.gemology.entities;

import net.minecraft.resources.Identifier;

public class GemDefinition {

    private final Identifier id;
    private final GemStats stats;
    private final GemDimensions dimensions;

    public GemDefinition(Identifier id, GemStats stats, GemDimensions dimensions) {
        this.id = id;
        this.stats = stats;
        this.dimensions = dimensions;
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
}