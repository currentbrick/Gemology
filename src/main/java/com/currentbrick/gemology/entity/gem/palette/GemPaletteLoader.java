package com.currentbrick.gemology.entity.gem.palette;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GemPaletteLoader {

    public static GemPalette load(ResourceManager resourceManager, Identifier texture) throws IOException {

        Optional<Resource> resource = resourceManager.getResource(texture);

        if (resource.isEmpty()) {
            throw new IOException("Could not find palette: " + texture);
        }

        try (InputStream inputStream = resource.get().open();
             NativeImage image = NativeImage.read(inputStream)) {

            int width = image.getWidth();
            int height = image.getHeight();

            int[] pixels = image.getPixelsABGR();

            List<List<Integer>> rows = new ArrayList<>();

            for (int y = 0; y < height; y++) {

                List<Integer> row = new ArrayList<>();

                for (int x = 0; x < width; x++) {
                    row.add(pixels[y * width + x]);
                }

                rows.add(row);
            }

            return new GemPalette(rows);
        }
    }
}