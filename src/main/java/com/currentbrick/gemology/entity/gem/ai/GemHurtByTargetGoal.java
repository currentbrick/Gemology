package com.currentbrick.gemology.entity.gem.ai;

import com.currentbrick.gemology.entity.EntityGem;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class GemHurtByTargetGoal extends HurtByTargetGoal {

    private final EntityGem gem;

    public GemHurtByTargetGoal(EntityGem gem, Class<?>... ignoreDamageFromTheseTypes) {
        super(gem, ignoreDamageFromTheseTypes);
        this.gem = gem;
    }

    @Override
    public boolean canUse() {
        if (!super.canUse()) {
            return false;
        }

        return gem.canTarget(gem.getLastHurtByMob());
    }
}