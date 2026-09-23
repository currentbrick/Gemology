package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.worldgen.feature.DirectionalBlockFeature;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURE_TYPES =
            DeferredRegister.create(BuiltInRegistries.FEATURE_TYPE, Gemology.MODID);

    public static final Supplier<MapCodec<DirectionalBlockFeature>> DIRECTIONAL_BLOCK =
            FEATURE_TYPES.register(
                    "directional_block",
                    () -> DirectionalBlockFeature.CODEC
            );
}
