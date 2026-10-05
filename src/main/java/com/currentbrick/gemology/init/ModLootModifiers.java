package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.loot.GemologyLootModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Gemology.MODID);

    public static final Supplier<MapCodec<GemologyLootModifier>> GEMOLOGY_LOOT =
            LOOT_MODIFIERS.register("gemology_loot", () -> GemologyLootModifier.CODEC);
}