package com.currentbrick.gemology.datagen;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.init.ModBlocks;
import com.currentbrick.gemology.init.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Gemology.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        blockModels.createTrivialCube(ModBlocks.TUNGSTEN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TUNGSTEN_BLOCK.get());

        blockModels.createTrivialCube(ModBlocks.WHITE_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.GRAY_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.BLACK_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.BROWN_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.RED_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.ORANGE_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.YELLOW_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.LIME_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.GREEN_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.CYAN_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.BLUE_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.PURPLE_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.MAGENTA_CHROMA_CRYSTAL.get());
        blockModels.createTrivialCube(ModBlocks.PINK_CHROMA_CRYSTAL.get());


        itemModels.generateFlatItem(ModItems.RAW_TUNGSTEN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TUNGSTEN_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.TUNGSTEN_NUGGET.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.WHITE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIGHT_GRAY_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GRAY_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLACK_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BROWN_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PINK_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RED_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ORANGE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.YELLOW_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIME_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GREEN_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CYAN_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.LIGHT_BLUE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.MAGENTA_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PURPLE_CHROMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PRISMATIC_CHROMA.get(), ModelTemplates.FLAT_ITEM);


        itemModels.generateFlatItem(ModItems.RUBY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.JASPER.get(), ModelTemplates.FLAT_ITEM);
    }
}
