package com.currentbrick.gemology.entities.gem;

import java.util.UUID;

public class GemInstanceData {
    private final UUID instanceId;
    private final float quality;
    private final int variant;

    public GemInstanceData(UUID instanceId, float quality, int variant) {
        this.instanceId = instanceId;
        this.quality = quality;
        this.variant = variant;
    }

    public UUID getInstanceId() {
        return instanceId;
    }

    public float getQuality() {
        return quality;
    }

    public int getVariant() {
        return variant;
    }
}