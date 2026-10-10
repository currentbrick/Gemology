package com.currentbrick.gemology.entity.gem;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class GemCompositeTextureManager {

    private static final Map<TextureKey, Identifier> CACHE = new HashMap<>();

    private static int nextTextureId = 0;

    private GemCompositeTextureManager() {}

    private record TextureKey(
            Identifier skin,
            Identifier markings,
            int skinColour,
            int markingColour
    ) {}

    public static Identifier getCompositeTexture(Identifier skinId, Identifier markingId, int skinColour, int markingColour) {
        TextureKey key = new TextureKey(skinId, markingId, skinColour, markingColour);

        Identifier cached = CACHE.get(key);

        if (cached != null) {
            return cached;
        }

        ResourceManager resources = Minecraft.getInstance().getResourceManager();

        try (
                InputStream skinStream = resources.open(skinId);
             NativeImage skin = NativeImage.read(skinStream)
        ) {
            NativeImage marking = null;

            try {
                if (markingId != null) {
                    try (InputStream markingStream = resources.open(markingId)) {
                        marking = NativeImage.read(markingStream);
                    }
                }

                if (marking != null && (skin.getWidth() != marking.getWidth() || skin.getHeight() != marking.getHeight())) {
                    throw new IOException("Skin and marking textures have different dimensions: " + skinId + " and " + markingId);
                }

                NativeImage composite = new NativeImage(skin.getWidth(), skin.getHeight(), false);

                for (int y = 0; y < skin.getHeight(); y++) {
                    for (int x = 0; x < skin.getWidth(); x++) {

                        int skinPixel = skin.getPixel(x, y);

                        int skinAlpha = (skinPixel >>> 24) & 0xFF;

                        int skinGray = (((skinPixel >>> 16) & 0xFF) * 299 + ((skinPixel >>> 8) & 0xFF) * 587 + (skinPixel & 0xFF) * 114) / 1000;

                        int skinR = tintChannel(skinGray, (skinColour >>> 16) & 0xFF);

                        int skinG = tintChannel(skinGray, (skinColour >>> 8) & 0xFF);

                        int skinB = tintChannel(skinGray, skinColour & 0xFF);

                        int outR = skinR;
                        int outG = skinG;
                        int outB = skinB;
                        int outA = skinAlpha;

                        if (marking != null) {
                            int markingPixel = marking.getPixel(x, y);

                            int markingAlpha = (markingPixel >>> 24) & 0xFF;

                            int markingGray = (((markingPixel >>> 16) & 0xFF) * 299 + ((markingPixel >>> 8) & 0xFF) * 587 + (markingPixel & 0xFF) * 114) / 1000;

                            int markingR = tintChannel(markingGray, (markingColour >>> 16) & 0xFF);

                            int markingG = tintChannel(markingGray, (markingColour >>> 8) & 0xFF);

                            int markingB = tintChannel(markingGray, markingColour & 0xFF);

                            int remainingAlpha = 255 - markingAlpha;

                            int outputAlpha = markingAlpha + (skinAlpha * remainingAlpha + 127) / 255;

                            if (outputAlpha > 0) {
                                outR = blendChannel(skinR, skinAlpha, markingR, markingAlpha, outputAlpha);
                                outG = blendChannel(skinG, skinAlpha, markingG, markingAlpha, outputAlpha);
                                outB = blendChannel(skinB, skinAlpha, markingB, markingAlpha, outputAlpha);
                            }

                            outA = outputAlpha;
                        }

                        int pixel = (outA << 24) | (outR << 16) | (outG << 8) | outB;

                        composite.setPixel(x, y, pixel);
                    }
                }

                Identifier compositeId = Identifier.fromNamespaceAndPath("gemology", "dynamic/gem_composite/" + nextTextureId++);

                DynamicTexture texture = new DynamicTexture(() -> "gemology composite texture", composite);

                Minecraft.getInstance().getTextureManager().register(compositeId, texture);

                CACHE.put(key, compositeId);

                return compositeId;

            } finally {
                if (marking != null) {
                    marking.close();
                }
            }

        } catch (IOException exception) {
            throw new RuntimeException(
                    "Failed to composite gem texture " + skinId + " with markings " + markingId,
                    exception
            );
        }
    }

    private static int tintChannel(int gray, int tint) {
        return (gray * tint + 127) / 255;
    }

    private static int blendChannel(
            int skin,
            int skinAlpha,
            int marking,
            int markingAlpha,
            int outputAlpha
    ) {
        int remainingAlpha = 255 - markingAlpha;

        int numerator =
                marking * markingAlpha
                        + (skin * skinAlpha * remainingAlpha + 127) / 255;

        return Math.min(
                255,
                (numerator + outputAlpha / 2) / outputAlpha
        );
    }
}