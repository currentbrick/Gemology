package com.currentbrick.gemology.entity.gem.abilities.types;

import com.currentbrick.gemology.entity.EntityGem;
import com.currentbrick.gemology.entity.gem.abilities.Ability;
import com.currentbrick.gemology.entity.gem.abilities.AbilityDefinition;
import com.google.gson.JsonObject;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectAbility implements Ability {

    @Override
    public void execute(EntityGem gem, AbilityDefinition definition, LivingEntity target) {
        JsonObject data = definition.getData();
        Identifier effectId = Identifier.parse(data.get("effect").getAsString());
        int duration = data.get("duration").getAsInt();
        int amplifier = data.get("amplifier").getAsInt();

        Holder<MobEffect> effect = BuiltInRegistries.MOB_EFFECT.get(effectId).orElse(null);

        gem.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
        if (gem.getOwner() != null) gem.getOwner().addEffect(new MobEffectInstance(effect, duration, amplifier, false, false));
    }
}