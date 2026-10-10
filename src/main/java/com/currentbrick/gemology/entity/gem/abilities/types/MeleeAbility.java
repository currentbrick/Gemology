package com.currentbrick.gemology.entity.gem.abilities.types;

import com.currentbrick.gemology.entity.EntityFusion;
import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.abilities.Ability;
import com.currentbrick.gemology.entity.gem.abilities.AbilityDefinition;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class MeleeAbility implements Ability {

    @Override
    public void execute(EntityGem gem, AbilityDefinition definition, LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return;
        }

        if (!(gem.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        JsonObject data = definition.getData();

        if (data.has("effects")) {
            JsonArray effects = data.getAsJsonArray("effects");

            for (int i = 0; i < effects.size(); i++) {
                applyEffect(target, effects.get(i).getAsJsonObject()
                );
            }
        }

        if (data.has("particles")) {
            spawnParticles(serverLevel, target, data.getAsJsonArray("particles"));
        }
    }

    @Override
    public void execute(EntityFusion gem, AbilityDefinition definition, LivingEntity target) {
        if (target == null || !target.isAlive()) {
            return;
        }

        if (!(gem.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        JsonObject data = definition.getData();

        if (data.has("effects")) {
            JsonArray effects = data.getAsJsonArray("effects");

            for (int i = 0; i < effects.size(); i++) {
                applyEffect(target, effects.get(i).getAsJsonObject()
                );
            }
        }

        if (data.has("particles")) {
            spawnParticles(serverLevel, target, data.getAsJsonArray("particles"));
        }
    }

    private void applyEffect(LivingEntity target, JsonObject effectData) {
        Identifier effectId = Identifier.parse(effectData.get("type").getAsString());

        int duration = effectData.has("duration") ? effectData.get("duration").getAsInt() : 100;

        int amplifier = effectData.has("amplifier") ? effectData.get("amplifier").getAsInt() : 0;

        // fire

        if (effectId.equals(Identifier.fromNamespaceAndPath("minecraft", "fire"))) {
            target.setRemainingFireTicks(duration);
            return;
        }

        Holder<MobEffect> effect = BuiltInRegistries.MOB_EFFECT.get(effectId).orElse(null);

        if (effect == null) {
            System.err.println("Unknown ability effect: " + effectId);
            return;
        }

        target.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
    }

    private void spawnParticles(ServerLevel level, LivingEntity target, JsonArray particleData) {
        for (int i = 0; i < particleData.size(); i++) {
            JsonObject particle = particleData.get(i).getAsJsonObject();

            Identifier particleId = Identifier.parse(particle.get("type").getAsString());

            int count = particle.has("count") ? particle.get("count").getAsInt() : 1;

            if (particleId.equals(Identifier.fromNamespaceAndPath("minecraft", "flame"))) {
                level.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), count, target.getBbWidth() * 0.5, target.getBbHeight() * 0.5, target.getBbWidth() * 0.5, 0.05);
            }
        }
    }
}