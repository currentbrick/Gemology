package com.currentbrick.gemology.entity.fusion;

import com.currentbrick.gemology.entity.gem.GemDefinition;
import com.currentbrick.gemology.entity.gem.GemDimensions;
import com.currentbrick.gemology.entity.gem.GemInstanceData;
import com.currentbrick.gemology.entity.gem.GemStats;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;

import java.util.*;

public class FusionGenerator {

    public static long getFusionSeed(GemInstanceData first, GemInstanceData second) {
        UUID firstId = first.getInstanceId();
        UUID secondId = second.getInstanceId();

        String firstString = firstId.toString();
        String secondString = secondId.toString();

        if (firstString.compareTo(secondString) > 0) {
            String temp = firstString;
            firstString = secondString;
            secondString = temp;
        }

        return hash(firstString, secondString);
    }

    private static long hash(String first, String second) {
        long hash = 1125899906842597L;

        String combined = first + second;
        for (int i = 0; i < combined.length(); i++) {
            hash = 31 * hash + combined.charAt(i);
        }

        return hash;
    }

    public static GemInstanceData getInstanceData(CompoundTag tag) {
        UUID instanceId = UUID.fromString(
                tag.getString("InstanceId").orElseThrow()
        );

        float quality = tag.getFloat("Quality").orElse(1.0F);
        int variant = tag.getInt("Variant").orElse(-1);
        return new GemInstanceData(instanceId, quality, variant);
    }

    public static RandomSource createRandom(long seed) {
        return RandomSource.create(seed);
    }

    public static float generateStat(float firstStat, float secondStat, float firstQuality, float secondQuality, RandomSource random) {
        float average = (firstStat + secondStat) / 2.0F;

        float quality = (firstQuality + secondQuality) / 2.0F;

        float randomVariation = random.nextFloat() * 0.2F - 0.1F;

        float qualityModifier = (quality - 1.0F) * 0.5F;

        return average * (1.0F + randomVariation + qualityModifier);
    }

    public static GemStats generateStats(GemDefinition firstDefinition, GemInstanceData firstInstance, GemDefinition secondDefinition, GemInstanceData secondInstance, RandomSource random) {
        GemStats firstStats = firstDefinition.getStats();
        GemStats secondStats = secondDefinition.getStats();

        float health = generateStat(firstStats.getHealth(), secondStats.getHealth(), firstInstance.getQuality(), secondInstance.getQuality(), random);
        float strength = generateStat(firstStats.getStrength(), secondStats.getStrength(), firstInstance.getQuality(), secondInstance.getQuality(), random);
        float speed = generateStat(firstStats.getSpeed(), secondStats.getSpeed(), firstInstance.getQuality(), secondInstance.getQuality(), random);

        return new GemStats(health, strength, speed);
    }

    public static List<Identifier> generateAbilities(GemDefinition firstDefinition, GemDefinition secondDefinition, RandomSource random) {
        Set<Identifier> abilityPool = new LinkedHashSet<>();

        abilityPool.addAll(firstDefinition.getAbilities());
        abilityPool.addAll(secondDefinition.getAbilities());

        List<Identifier> abilities = new ArrayList<>(abilityPool);

        if (abilities.isEmpty()) {
            return List.of();
        }

        for (int i = abilities.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            Identifier temp = abilities.get(i);
            abilities.set(i, abilities.get(j));
            abilities.set(j, temp);
        }

        int maxAbilities = Math.min(3, abilities.size());
        int abilityCount = 1 + random.nextInt(maxAbilities);

        return new ArrayList<>(abilities.subList(0, abilityCount));
    }

    public static GemDimensions generateDimensions(GemDefinition firstDefinition, GemInstanceData firstInstance, GemDefinition secondDefinition, GemInstanceData secondInstance, RandomSource random) {
        GemDimensions firstDimensions = firstDefinition.getDimensions();
        GemDimensions secondDimensions = secondDefinition.getDimensions();

        float width = generateDimension(firstDimensions.getWidth(), secondDimensions.getWidth(), firstInstance.getQuality(), secondInstance.getQuality(), random);

        float height = generateDimension(firstDimensions.getHeight(), secondDimensions.getHeight(), firstInstance.getQuality(), secondInstance.getQuality(), random);

        return new GemDimensions(width, height);
    }


    private static float generateDimension(float first, float second, float firstQuality, float secondQuality, RandomSource random) {
        float average = (first + second) / 2.0F;
        float quality = (firstQuality + secondQuality) / 2.0F;
        float randomVariation = random.nextFloat() * 0.10F - 0.05F;
        float qualityModifier = (quality - 1.0F) * 0.25F;
        return average * (1.0F + randomVariation + qualityModifier);
    }
}
