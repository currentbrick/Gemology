package com.currentbrick.gemology.entity.fusion.ai;

import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.gem.ai.MovementMode;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class FusionWanderGoal extends RandomStrollGoal {

    private final EntityFusion gem;

    public FusionWanderGoal(EntityFusion gem, double speedModifier) {
        super(gem, speedModifier);
        this.gem = gem;
    }

    @Override
    public boolean canUse() {
        return gem.getMovementMode() == MovementMode.WANDER || !gem.hasOwner() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return gem.getMovementMode() == MovementMode.WANDER || !gem.hasOwner() && super.canContinueToUse();
    }
}