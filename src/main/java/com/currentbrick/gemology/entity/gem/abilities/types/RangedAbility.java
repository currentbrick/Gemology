package com.currentbrick.gemology.entity.gem.abilities.types;

import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.abilities.Ability;
import com.currentbrick.gemology.entity.gem.abilities.AbilityDefinition;
import com.google.gson.JsonObject;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.phys.Vec3;

public class RangedAbility implements Ability {

    @Override
    public void execute(EntityGem gem, AbilityDefinition definition, LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return;
        }

        if (gem.level().isClientSide()) {
            return;
        }

        JsonObject data = definition.getData();

        if (!data.has("projectile")) {
            return;
        }

        JsonObject projectile = data.getAsJsonObject("projectile");

        String type = projectile.get("type").getAsString();

        if (type.equals("minecraft:large_fireball")) {
            spawnLargeFireball(gem, target, projectile);
        }
    }

    private void spawnLargeFireball(EntityGem gem, LivingEntity target, JsonObject data) {
        int explosionPower = data.has("explosion_power") ? data.get("explosion_power").getAsInt() : 1;

        Vec3 direction = target.position().add(0, target.getEyeHeight() * 0.5, 0).subtract(gem.getEyePosition()).normalize();

        LargeFireball fireball = new LargeFireball(gem.level(), gem, direction, explosionPower);

        fireball.setPos(gem.getX(), gem.getEyeY(), gem.getZ());

        gem.level().addFreshEntity(fireball);
    }
}