package com.currentbrick.gemology.entity.fusion.ai;

import com.currentbrick.gemology.entity.EntityFusion;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class FusionMeleeAttackGoal extends MeleeAttackGoal {

    public FusionMeleeAttackGoal(EntityFusion gem, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(gem, speedModifier, followingTargetEvenIfNotSeen);
    }
}