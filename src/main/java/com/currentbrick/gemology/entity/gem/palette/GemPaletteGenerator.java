package com.currentbrick.gemology.entity.gem.palette;

import java.util.UUID;

public class GemPaletteGenerator {

    public enum PaletteType {
        SKIN(0x01),
        HAIR(0x02),
        GEM(0x03),
        OUTFIT(0x04),
        INSIGNIA(0x05),
        MARKINGS(0x06);

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
}