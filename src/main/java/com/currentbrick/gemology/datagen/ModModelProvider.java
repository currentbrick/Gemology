package com.currentbrick.gemology.datagen;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.client.item.GemVariantProperty;
import com.currentbrick.gemology.entities.gem.GemDefinition;
import com.currentbrick.gemology.entities.gem.GemVariant;
import com.currentbrick.gemology.init.ModBlocks;
import com.currentbrick.gemology.init.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


        itemModels.generateFlatItem(ModItems.FUSION.get(), ModelTemplates.FLAT_ITEM);


        itemModels.generateFlatItem(ModItems.RUBY.get(), ModelTemplates.FLAT_ITEM);

        createGemItemModel(
                itemModels,
                ModItems.JASPER.get(),
                List.of(
                        new GemItemVariant(0, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/zebra")),
                        new GemItemVariant(1, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/red_striped")),
                        new GemItemVariant(2, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/ripple")),
                        new GemItemVariant(3, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/imperial")),
                        new GemItemVariant(4, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/golden")),
                        new GemItemVariant(5, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/rainforest")),
                        new GemItemVariant(6, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/mookaite")),
                        new GemItemVariant(7, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/matrix")),
                        new GemItemVariant(8, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/picasso")),
                        new GemItemVariant(9, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/ocean")),
                        new GemItemVariant(10, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/royal_plume")),
                        new GemItemVariant(11, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/blue_snakeskin")),
                        new GemItemVariant(12, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/biggs")),
                        new GemItemVariant(13, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/kambaba")),
                        new GemItemVariant(14, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/flame")),
                        new GemItemVariant(15, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/black"))
                )
        );
    }


    private void createGemItemModel(ItemModelGenerators itemModels, Item item, List<GemItemVariant> variants) {
        List<SelectItemModel.SwitchCase<Integer>> cases = new ArrayList<>();

        for (GemItemVariant variant : variants) {

            Identifier modelId = variant.texture();

            TextureMapping mapping = new TextureMapping().put(TextureSlot.LAYER0, new Material(variant.texture()));

            ModelTemplates.FLAT_ITEM.create(modelId, mapping, itemModels.modelOutput);

            cases.add(new SelectItemModel.SwitchCase<>(List.of(variant.id()), ItemModelUtils.plainModel(modelId)));
        }

        itemModels.itemModelOutput.accept(item, new SelectItemModel.Unbaked(Optional.empty(), new SelectItemModel.UnbakedSwitch<>(new GemVariantProperty(), cases), Optional.empty()));
    }

    private record GemItemVariant(int id, Identifier texture) {}
}
