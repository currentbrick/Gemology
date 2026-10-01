package com.currentbrick.gemology.entity.ai;

public enum MovementMode {
    WANDER,
    FOLLOW_OWNER,
    STAY;

    public MovementMode next() {
        return switch (this) {
            case WANDER -> FOLLOW_OWNER;
            case FOLLOW_OWNER -> STAY;
            case STAY -> WANDER;
        };
    }
}
