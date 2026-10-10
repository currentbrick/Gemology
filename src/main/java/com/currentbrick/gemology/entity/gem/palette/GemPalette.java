package com.currentbrick.gemology.entity.gem.palette;

import java.util.List;

public class GemPalette {

    private final List<List<Integer>> rows;

    public GemPalette(List<List<Integer>> rows) {
        this.rows = rows;
    }

    public List<Integer> getRow(int row) {
        return rows.get(row);
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColour(int row, float position) {

        List<Integer> colours = rows.get(row);

        if (colours.isEmpty()) {
            return 0;
        }

        if (colours.size() == 1) {
            return convertABGR(colours.get(0));
        }

        position = Math.max(0.0F, Math.min(1.0F, position));

        float scaledPosition = position * (colours.size() - 1);

        int lowerIndex = (int) Math.floor(scaledPosition);
        int upperIndex = Math.min(lowerIndex + 1, colours.size() - 1);

        float interpolation = scaledPosition - lowerIndex;

        int lower = colours.get(lowerIndex);
        int upper = colours.get(upperIndex);


        return interpolateABGR(lower, upper, interpolation);
    }

    private static int interpolateABGR(int colour1, int colour2, float t) {
        int a1 = (colour1 >> 24) & 0xFF;
        int b1 = (colour1 >> 16) & 0xFF;
        int g1 = (colour1 >> 8) & 0xFF;
        int r1 = colour1 & 0xFF;

        int a2 = (colour2 >> 24) & 0xFF;
        int b2 = (colour2 >> 16) & 0xFF;
        int g2 = (colour2 >> 8) & 0xFF;
        int r2 = colour2 & 0xFF;

        int a = (int) (a1 + (a2 - a1) * t);
        int r = (int) (r1 + (r2 - r1) * t);
        int g = (int) (g1 + (g2 - g1) * t);
        int b = (int) (b1 + (b2 - b1) * t);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }


    private static int convertABGR(int colour) {
        int a = (colour >> 24) & 0xFF;
        int b = (colour >> 16) & 0xFF;
        int g = (colour >> 8) & 0xFF;
        int r = colour & 0xFF;

        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}