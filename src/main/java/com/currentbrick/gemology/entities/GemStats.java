package com.currentbrick.gemology.entities;

public class GemStats {

    private final float health;
    private final float strength;
    private final float speed;

    public GemStats(float health, float strength, float speed) {
        this.health = health;
        this.strength = strength;
        this.speed = speed;
    }

    public float getHealth() {
        return health;
    }

    public float getStrength() {
        return strength;
    }

    public float getSpeed() {
        return speed;
    }
}