package com.currentbrick.gemology.entity.gem.ai;

import com.currentbrick.gemology.entity.EntityGem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class GemRangedAttackGoal extends Goal {

    private final EntityGem gem;
    private final double speedModifier;

    private int attackTime = -1;

    private static final int ATTACK_INTERVAL = 60;
    private static final double MIN_ATTACK_DISTANCE = 6.0D;
    private static final double MAX_ATTACK_DISTANCE = 16.0D;

    public GemRangedAttackGoal(EntityGem gem, double speedModifier) {
        this.gem = gem;
        this.speedModifier = speedModifier;

        setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (!gem.hasRangedAbility()) {
            return false;
        }

        LivingEntity target = gem.getTarget();

        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = gem.getTarget();

        return gem.hasRangedAbility() && target != null && target.isAlive();
    }

    @Override
    public void start() {
        attackTime = 0;
    }

    @Override
    public void stop() {
        attackTime = -1;
        gem.getNavigation().stop();
    }

    @Override
    public void tick() {

        LivingEntity target = gem.getTarget();

        if (target == null) {
            return;
        }

        double distanceSquared = gem.distanceToSqr(target);

        gem.getLookControl().setLookAt(target);

        if (distanceSquared > MAX_ATTACK_DISTANCE * MAX_ATTACK_DISTANCE) {
            gem.getNavigation().moveTo(target, speedModifier);
            return;
        }

        if (distanceSquared < MIN_ATTACK_DISTANCE * MIN_ATTACK_DISTANCE) {
            gem.getNavigation().stop();
            return;
        }

        gem.getNavigation().stop();

        if (attackTime > 0) {
            attackTime--;
            return;
        }

        gem.performSecondaryAttack(target);

        attackTime = ATTACK_INTERVAL;
    }
}