package com.currentbrick.gemology.entities.gem.abilities;

import net.minecraft.resources.Identifier;

public enum AbilityTrigger {

    PASSIVE(Identifier.fromNamespaceAndPath("gemology", "passive"));

    private final Identifier id;

    AbilityTrigger(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return id;
    }

    public static AbilityTrigger fromId(Identifier id) {
        for (AbilityTrigger trigger : values()) {
            if (trigger.id.equals(id)) {
                return trigger;
            }
        }

        return null;
    }
}