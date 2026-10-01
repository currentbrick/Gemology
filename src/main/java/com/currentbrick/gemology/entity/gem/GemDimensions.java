package com.currentbrick.gemology.entity.gem;

public class GemDimensions {

    private final float width;
    private final float height;

    public GemDimensions(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}