package com.currentbrick.gemology.entity.gem.ai;

import com.currentbrick.gemology.entity.EntityGem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GemMeleeAttackGoal extends MeleeAttackGoal {

    private final EntityGem gem;

    public GemMeleeAttackGoal(EntityGem gem, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(gem, speedModifier, followingTargetEvenIfNotSeen);
        this.gem = gem;
    }

    @Override
    public boolean canUse() {
        if (!gem.hasMeleeAbility()) {
            return false;
        }

        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if (!gem.hasMeleeAbility()) {
            return false;
        }

        return super.canContinueToUse();
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        super.checkAndPerformAttack(target);
    }
}