package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.worldgen.feature.DirectionalBlockFeature;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ModFeatures {
    public static final DeferredRegister<MapCodec<? extends Feature>> FEATURE_TYPES =
            DeferredRegister.create(BuiltInRegistries.FEATURE_TYPE, Gemology.MODID);

    public static final Supplier<MapCodec<DirectionalBlockFeature>> DIRECTIONAL_BLOCK =
            FEATURE_TYPES.register(
                    "directional_block",
                    () -> DirectionalBlockFeature.CODEC
            );

    public static final ResourceKey<Feature> OVERWORLD_TUNGSTEN_ORE = registerKey("overworld_tungsten_ore");


    public static void bootstrap(BootstrapContext<Feature> context) {

        Gemology.LOGGER.error("========== BOOTSTRAPPING TUNGSTEN FEATURE ==========");


        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        context.register(OVERWORLD_TUNGSTEN_ORE, new OreFeature(List.of(
                                BlockReplacement.replace(stoneReplaceables, ModBlocks.TUNGSTEN_ORE.get().defaultBlockState()),
                                BlockReplacement.replace(deepslateReplaceables, ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get().defaultBlockState())
                        ), 9));
    }

    private static ResourceKey<Feature> registerKey(String name) {
        return ResourceKey.create(
                Registries.FEATURE,
                Identifier.fromNamespaceAndPath(Gemology.MODID, name)
        );

    }

}
