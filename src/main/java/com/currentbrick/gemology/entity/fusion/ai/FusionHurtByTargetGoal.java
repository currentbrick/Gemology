package com.currentbrick.gemology.entity.fusion.ai;

import com.currentbrick.gemology.entity.EntityFusion;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class FusionHurtByTargetGoal extends HurtByTargetGoal {

    private final EntityFusion gem;

    public FusionHurtByTargetGoal(EntityFusion gem, Class<?>... ignoreDamageFromTheseTypes) {
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