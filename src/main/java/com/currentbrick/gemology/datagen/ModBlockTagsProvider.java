package com.currentbrick.gemology.datagen;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider) {

        super(output, lookupProvider, Gemology.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.TUNGSTEN_ORE.getKey())
                .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.getKey())
                .add(ModBlocks.TUNGSTEN_BLOCK.getKey())
                .add(ModBlocks.RAW_TUNGSTEN_BLOCK.getKey())
                .add(ModBlocks.INCUBATOR.getKey())
                .add(ModBlocks.WHITE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.ORANGE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.MAGENTA_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.YELLOW_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.LIME_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.PINK_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.GRAY_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.CYAN_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.PURPLE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.BLUE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.BROWN_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.GREEN_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.RED_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.BLACK_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.DRAINED_STONE.getKey())
                .add(ModBlocks.RUINED_MARBLE_BLOCK.getKey())
                .add(ModBlocks.RUINED_MARBLE_BRICK.getKey())
                .add(ModBlocks.RUINED_MARBLE_PILLAR.getKey())
                .add(ModBlocks.CHISELED_RUINED_MARBLE.getKey())
                .add(ModBlocks.SMOOTH_RUINED_MARBLE.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.TUNGSTEN_ORE.getKey())
                .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.getKey())
                .add(ModBlocks.RAW_TUNGSTEN_BLOCK.getKey())
                .add(ModBlocks.TUNGSTEN_BLOCK.getKey())
                .add(ModBlocks.WHITE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.ORANGE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.MAGENTA_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.YELLOW_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.LIME_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.PINK_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.GRAY_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.CYAN_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.PURPLE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.BLUE_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.BROWN_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.GREEN_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.RED_CHROMA_CRYSTAL.getKey())
                .add(ModBlocks.BLACK_CHROMA_CRYSTAL.getKey());
    }
}