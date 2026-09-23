package com.currentbrick.gemology.entities.ai;

import com.currentbrick.gemology.entities.EntityGem;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class GemWanderGoal extends RandomStrollGoal {

    private final EntityGem gem;

    public GemWanderGoal(EntityGem gem, double speedModifier) {
        super(gem, speedModifier);
        this.gem = gem;
    }

    @Override
    public boolean canUse() {
        return gem.getMovementMode() == MovementMode.WANDER
            || !gem.hasOwner() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return gem.getMovementMode() == MovementMode.WANDER
                || !gem.hasOwner() && super.canContinueToUse();
    }
}