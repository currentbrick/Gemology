package com.currentbrick.gemology.init;

import com.currentbrick.gemology.entities.EntityFusion;
import com.currentbrick.gemology.entities.EntityGem;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModEntityAttributes {

    public static AttributeSupplier.Builder createGemAttributes() {
        return EntityGem.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 2.0);

    }

    public static AttributeSupplier.Builder createFusionAttributes() {
        return EntityFusion.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 2.0);
    }
}
