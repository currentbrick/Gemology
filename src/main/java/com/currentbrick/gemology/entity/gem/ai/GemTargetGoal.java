package com.currentbrick.gemology.entity.gem.ai;

import com.currentbrick.gemology.entity.EntityGem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

import java.util.EnumSet;
import java.util.List;

public class GemTargetGoal extends TargetGoal {

    private final EntityGem gem;

    public GemTargetGoal(EntityGem gem) {
        super(gem, false);
        this.gem = gem;

        setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (gem.getTarget() != null && gem.getTarget().isAlive()) {
            return false;
        }

        List<LivingEntity> targets = gem.level().getEntitiesOfClass(LivingEntity.class, gem.getBoundingBox().inflate(16.0D), gem::canTarget);

        if (targets.isEmpty()) {
            return false;
        }

        LivingEntity closest = targets.get(0);

        double closestDistance = gem.distanceToSqr(closest);

        for (LivingEntity target : targets) {
            double distance = gem.distanceToSqr(target);

            if (distance < closestDistance) {
                closest = target;
                closestDistance = distance;
            }
        }

        gem.setTarget(closest);
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = gem.getTarget();

        return target != null && target.isAlive() && gem.canTarget(target);
    }

    @Override
    public void stop() {
        gem.setTarget(null);
    }
}