package com.currentbrick.gemology.datagen;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.client.item.GemVariantProperty;
import com.currentbrick.gemology.init.ModBlocks;
import com.currentbrick.gemology.init.ModItems;
import com.mojang.math.Quadrant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider {

    public ModModelProvider(PackOutput output) {
        super(output, Gemology.MODID);
    }

    private static TexturedModel.Provider ruinedMarblePillarModel() {
        return TexturedModel.COLUMN;
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        TextureMapping incubatorTextures = new TextureMapping()
                .put(TextureSlot.TOP, new Material(
                        Identifier.fromNamespaceAndPath(Gemology.MODID, "block/incubator_top")))
                .put(TextureSlot.BOTTOM, new Material(
                        Identifier.fromNamespaceAndPath(Gemology.MODID, "block/incubator_bottom")))
                .put(TextureSlot.SIDE, new Material(
                        Identifier.fromNamespaceAndPath(Gemology.MODID, "block/incubator_side")))
                .put(TextureSlot.FRONT, new Material(
                        Identifier.fromNamespaceAndPath(Gemology.MODID, "block/incubator_front")));

        blockModels.createTrivialCube(ModBlocks.TUNGSTEN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_TUNGSTEN_ORE.get());
        blockModels.createTrivialCube(ModBlocks.TUNGSTEN_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.RAW_TUNGSTEN_BLOCK.get());

        blockModels.createTrivialCube(ModBlocks.RUINED_MARBLE_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.RUINED_MARBLE_BRICK.get());
        blockModels.createTrivialCube(ModBlocks.SMOOTH_RUINED_MARBLE.get());
        blockModels.createTrivialCube(ModBlocks.CHISELED_RUINED_MARBLE.get());

        blockModels.createAxisAlignedPillarBlock(ModBlocks.RUINED_MARBLE_PILLAR.get(), ruinedMarblePillarModel());

        blockModels.createTrivialCube(ModBlocks.DRAINED_SOIL.get());
        blockModels.createTrivialCube(ModBlocks.DRAINED_STONE.get());

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

        itemModels.generateFlatItem(ModItems.CHROMA_CATALYST.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.WHITE_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.YELLOW_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_ESSENCE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.PINK_ESSENCE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.INACTIVE_JASPER_BASE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.INACTIVE_QUARTZ_BASE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.INACTIVE_AGATE_BASE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.INACTIVE_RUBY_BASE.get(), ModelTemplates.FLAT_ITEM);


        itemModels.generateFlatItem(ModItems.FUSION.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.RUBY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BLUE_RUBY.get(), ModelTemplates.FLAT_ITEM);

        createGemItemModel(itemModels, ModItems.JASPER.get(),
                List.of(
                        new GemItemVariant(0, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/dalmatian")),
                        new GemItemVariant(1, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/red_striped")),
                        new GemItemVariant(2, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/mookaite")),
                        new GemItemVariant(3, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/picture")),
                        new GemItemVariant(4, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/yellow")),
                        new GemItemVariant(5, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/rainforest")),
                        new GemItemVariant(6, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/butterfly")),
                        new GemItemVariant(7, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/picasso")),
                        new GemItemVariant(8, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/silver_leaf")),
                        new GemItemVariant(9, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/ocean")),
                        new GemItemVariant(10, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/lavender")),
                        new GemItemVariant(11, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/k2")),
                        new GemItemVariant(12, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/biggs")),
                        new GemItemVariant(13, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/bloodstone")),
                        new GemItemVariant(14, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/red")),
                        new GemItemVariant(15, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/jasper/black"))
                )
        );
        createGemItemModel(itemModels, ModItems.QUARTZ.get(),
                List.of(
                        new GemItemVariant(0, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/milky")),
                        new GemItemVariant(1, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/chert")),
                        new GemItemVariant(2, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/cherry")),
                        new GemItemVariant(3, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/blue_aventurine")),
                        new GemItemVariant(4, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/citrine")),
                        new GemItemVariant(5, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/prasiolite")),
                        new GemItemVariant(6, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/rose")),
                        new GemItemVariant(7, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/flint")),
                        new GemItemVariant(8, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/smoky")),
                        new GemItemVariant(9, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/chalcedony")),
                        new GemItemVariant(10, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/amethyst")),
                        new GemItemVariant(11, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/dumortierite")),
                        new GemItemVariant(12, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/tigers_eye")),
                        new GemItemVariant(13, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/heliotrope")),
                        new GemItemVariant(14, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/carnelian")),
                        new GemItemVariant(15, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/quartz/onyx"))
                )
        );
        createGemItemModel(itemModels, ModItems.AGATE.get(),
                List.of(
                        new GemItemVariant(0, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/white")),
                        new GemItemVariant(1, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/sardonyx")),
                        new GemItemVariant(2, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/agua_nueva")),
                        new GemItemVariant(3, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/blue_lace")),
                        new GemItemVariant(4, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/kentucky")),
                        new GemItemVariant(5, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/chrysoprase")),
                        new GemItemVariant(6, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/apricot")),
                        new GemItemVariant(7, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/botswana")),
                        new GemItemVariant(8, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/montana")),
                        new GemItemVariant(9, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/ellensburg_blue")),
                        new GemItemVariant(10, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/grape")),
                        new GemItemVariant(11, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/holly_blue")),
                        new GemItemVariant(12, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/brazilian")),
                        new GemItemVariant(13, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/moss")),
                        new GemItemVariant(14, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/lake_superior")),
                        new GemItemVariant(15, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/onyx")),
                        new GemItemVariant(16, Identifier.fromNamespaceAndPath(Gemology.MODID, "item/agate/iris"))
                )
        );

        ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM.create(ModBlocks.INCUBATOR.get(), incubatorTextures, blockModels.modelOutput);

        Identifier incubatorModel = Identifier.fromNamespaceAndPath(
                Gemology.MODID,
                "block/incubator"
        );

        MultiVariant incubatorVariant = new MultiVariant(WeightedList.of(new Variant(incubatorModel)));

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(ModBlocks.INCUBATOR.get())
                        .with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING)
                                .select(Direction.NORTH, incubatorVariant)
                                .select(Direction.EAST, incubatorVariant.with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.SOUTH, incubatorVariant.with(VariantMutator.Y_ROT.withValue(Quadrant.R180)))
                                .select(Direction.WEST, incubatorVariant.with(VariantMutator.Y_ROT.withValue(Quadrant.R270))))
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


    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return super.getKnownBlocks().filter(holder ->
                !holder.is(ModBlocks.WHITE_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.GRAY_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.BLACK_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.BROWN_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.RED_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.ORANGE_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.YELLOW_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.LIME_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.GREEN_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.CYAN_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.BLUE_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.PURPLE_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.MAGENTA_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.PINK_CHROMA_CRYSTAL.getKey()))
                .filter(holder -> !holder.is(ModBlocks.CHROMA_CLUSTER_CROP.getKey())


        );
    }
}
