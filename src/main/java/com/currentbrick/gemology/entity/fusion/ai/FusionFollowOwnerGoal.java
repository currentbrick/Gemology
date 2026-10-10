package com.currentbrick.gemology.entity.fusion.ai;

import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.gem.ai.MovementMode;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class FusionFollowOwnerGoal extends Goal {

    private final EntityFusion gem;
    private final double speed;
    private final float minDistance;
    private final float maxDistance;

    private Player owner;

    public FusionFollowOwnerGoal(EntityFusion gem, double speed, float minDistance, float maxDistance) {
        this.gem = gem;
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (gem.getMovementMode() != MovementMode.FOLLOW_OWNER) {
            return false;
        }

        owner = gem.getOwner();

        if (owner == null) {
            return false;
        }

        return gem.distanceTo(owner) > maxDistance;
    }

    @Override
    public boolean canContinueToUse() {
        if (gem.getMovementMode() != MovementMode.FOLLOW_OWNER) {
            return false;
        }

        if (owner == null || !owner.isAlive()) {
            return false;
        }

        return gem.distanceTo(owner) > minDistance;
    }

    @Override
    public void start() {
        gem.getNavigation().moveTo(owner, speed);
    }

    @Override
    public void tick() {
        gem.getNavigation().moveTo(owner, speed);
        gem.getLookControl().setLookAt(owner);
    }

    @Override
    public void stop() {
        owner = null;
        gem.getNavigation().stop();
    }
}