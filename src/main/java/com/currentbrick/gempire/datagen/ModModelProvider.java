package com.currentbrick.gempire.datagen;

import com.currentbrick.gempire.Gemology;
import com.currentbrick.gempire.init.ModBlocks;
import com.currentbrick.gempire.init.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Gemology.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        blockModels.createTrivialCube(ModBlocks.TUNGSTEN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get());

        itemModels.generateFlatItem(ModItems.RAW_TUNGSTEN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TUNGSTEN_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TUNGSTEN_NUGGET.get(), ModelTemplates.FLAT_ITEM);

    }
}
