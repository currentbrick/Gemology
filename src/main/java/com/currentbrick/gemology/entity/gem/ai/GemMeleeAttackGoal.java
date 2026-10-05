package com.currentbrick.gemology.entity.gem.ai;

import com.currentbrick.gemology.entity.EntityGem;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class GemMeleeAttackGoal extends MeleeAttackGoal {

    public GemMeleeAttackGoal(EntityGem gem, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        super(gem, speedModifier, followingTargetEvenIfNotSeen);
    }
}