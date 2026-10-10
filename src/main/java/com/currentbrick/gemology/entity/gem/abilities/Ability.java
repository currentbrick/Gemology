package com.currentbrick.gemology.entity.gem.abilities;

import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
import net.minecraft.world.entity.LivingEntity;

public interface Ability {

    void execute(EntityGem gem, AbilityDefinition definition, LivingEntity target);

    void execute(EntityFusion gem, AbilityDefinition definition, LivingEntity target);
}