package com.currentbrick.gemology.entity.gem.palette;

import java.util.UUID;

public class GemPaletteGenerator {

    public enum PaletteType {
        SKIN(0x01),
        HAIR(0x02),
        GEM(0x03),
        OUTFIT(0x04),
        INSIGNIA(0x05),
        MARKINGS(0x06),
        WINGS(0x07);

        private final long salt;

        PaletteType(long salt) {
            this.salt = salt;
        }

        public long getSalt() {
            return salt;
        }
    }

    public static int generate(GemPalette palette, int colourVariant, UUID instanceId, PaletteType type) {
        if (palette == null || instanceId == null) {
            return 0;
        }

        if (colourVariant < 0 || colourVariant >= palette.getRowCount()) {
            return 0;
        }

        long seed = instanceId.getMostSignificantBits()
                ^ instanceId.getLeastSignificantBits()
                ^ type.getSalt();

        long positive = seed & Long.MAX_VALUE;

        float position = positive / (float) Long.MAX_VALUE;

        return palette.getColour(colourVariant, position);
    }

    public static int combineColours(int colour1, int colour2) {
        int r1 = (colour1 >> 16) & 0xFF;
        int g1 = (colour1 >> 8) & 0xFF;
        int b1 = colour1 & 0xFF;

        int r2 = (colour2 >> 16) & 0xFF;
        int g2 = (colour2 >> 8) & 0xFF;
        int b2 = colour2 & 0xFF;

        int r = (r1 + r2) / 2;
        int g = (g1 + g2) / 2;
        int b = (b1 + b2) / 2;

        // RGB -> HSB
        float[] hsb = java.awt.Color.RGBtoHSB(r, g, b, null);

        // increase saturation
        hsb[1] = Math.min(1.0F, hsb[1] * 1.4F);

        // increase brightness
        hsb[2] = Math.min(1.0F, hsb[2] * 1.1F);

        // HSB -> RGB
        int rgb = java.awt.Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

        return 0xFF000000 | (rgb & 0xFFFFFF);
    }
}