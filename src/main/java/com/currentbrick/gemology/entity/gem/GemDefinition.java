package com.currentbrick.gemology.entity.gem;

import net.minecraft.resources.Identifier;

import java.time.LocalDate;
import java.util.List;

public class GemDefinition {

    private final Identifier id;
    private final GemStats stats;
    private final GemDimensions dimensions;
    private final List<Identifier> abilities;
    private final List<GemVariant> variants;
    private final int skinVariants;
    private final int hairVariants;
    private final int gemVariants;
    private final int outfitVariants;
    private final int insigniaVariants;
    private final List<GemAvailability> availability;

    public GemDefinition(Identifier id, GemStats stats, GemDimensions dimensions, List<Identifier> abilities, List<GemVariant> variants, int skinVariants, int hairVariants, int gemVariants, int outfitVariants, int insigniaVariants, List<GemAvailability> availability) {
        this.id = id;
        this.stats = stats;
        this.dimensions = dimensions;
        this.abilities = abilities;
        this.variants = variants;
        this.skinVariants = skinVariants;
        this.hairVariants = hairVariants;
        this.gemVariants = gemVariants;
        this.outfitVariants = outfitVariants;
        this.insigniaVariants = insigniaVariants;
        this.availability = availability;
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

    public List<GemVariant> getVariants() {
        return variants;
    }

    public GemVariant getVariant(int id) {
        for (GemVariant variant : variants) {
            if (variant.getId() == id) {
                return variant;
            }
        }

        return null;
    }

    public int getSkinVariants() {
        return skinVariants;
    }

    public int getHairVariants() {
        return hairVariants;
    }

    public int getGemVariants() {
        return gemVariants;
    }

    public int getOutfitVariants() {
        return outfitVariants;
    }

    public int getInsigniaVariants() {
        return insigniaVariants;
    }

    public List<GemAvailability> getAvailability() {
        return availability;
    }

    public boolean isAvailable(LocalDate date) {
        if (availability.isEmpty()) {
            return true;
        }

        return availability.stream()
                .anyMatch(period -> period.isAvailable(date));
    }
}