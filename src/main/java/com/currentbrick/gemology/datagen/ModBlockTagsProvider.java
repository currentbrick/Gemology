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
                .add(ModBlocks.TUNGSTEN_BLOCK.getKey());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.TUNGSTEN_ORE.getKey())
                .add(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.getKey())
                .add(ModBlocks.TUNGSTEN_BLOCK.getKey());
    }
}