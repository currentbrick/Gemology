package com.currentbrick.gemology.entities.gem;

import net.minecraft.resources.Identifier;

public class GemVariant {

    private final int id;
    private final String name;
    private final Identifier chromaId;
    private final Identifier itemTexture;

    public GemVariant(int id, String name, Identifier chromaId, Identifier itemTexture) {
        this.id = id;
        this.name = name;
        this.chromaId = chromaId;
        this.itemTexture = itemTexture;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Identifier getChromaId() {
        return chromaId;
    }

    public Identifier getItemTexture() {
        return itemTexture;
    }
}