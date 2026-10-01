package com.currentbrick.gemology.entity.gem.abilities;

import com.google.gson.JsonObject;
import net.minecraft.resources.Identifier;

public class AbilityDefinition {

    private final Identifier id;
    private final Identifier type;
    private final Identifier trigger;
    private final JsonObject data;

    public AbilityDefinition(Identifier id, Identifier type, Identifier trigger, JsonObject data) {
        this.id = id;
        this.type = type;
        this.trigger = trigger;
        this.data = data;
    }

    public Identifier getId() {
        return id;
    }

    public Identifier getType() {
        return type;
    }

    public JsonObject getData() {
        return data;
    }

    public Identifier getTrigger() {
        return trigger;
    }
}
