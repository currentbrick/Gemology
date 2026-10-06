package com.currentbrick.gemology.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> BIO_POISON = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath("gemology", "bio_poison"));

    public static DamageSource bioPoison(ServerLevel level) {
        return new DamageSource(level.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(BIO_POISON));
    }
}