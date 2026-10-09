package com.currentbrick.gemology.entity.gem;

import net.minecraft.util.RandomSource;

import java.util.UUID;

public final class GemStatCalculator {

    private static final float MAX_ADJUSTMENT = 1.0F / 3.0F;

    private GemStatCalculator() {}

    public static float calculateStat(float baseStat, float quality, UUID instanceId, String statName) {
        if (instanceId == null) {
            return baseStat;
        }

        quality = Math.clamp(quality, 0.0F, 2.0F);

        if (quality == 1.0F) {
            return baseStat;
        }

        long seed = instanceId.getMostSignificantBits() ^ instanceId.getLeastSignificantBits() ^ statName.hashCode();

        RandomSource random = RandomSource.create(seed);

        float randomFactor = random.nextFloat();

        float qualityFactor = Math.abs(quality - 1.0F);

        float adjustment = randomFactor * qualityFactor * MAX_ADJUSTMENT;

        if (quality < 1.0F) {
            adjustment = -adjustment;
        }

        return baseStat * (1.0F + adjustment);
    }
}