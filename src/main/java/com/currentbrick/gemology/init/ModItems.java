package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.item.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Gemology.MODID);

    private static final Map<Identifier, DeferredItem<Item>> GEM_ITEMS = new HashMap<>();

    // ----------- ITEMS -----------

    public static final DeferredItem<Item> RAW_TUNGSTEN = ITEMS.registerSimpleItem("raw_tungsten");
    public static final DeferredItem<Item> TUNGSTEN_INGOT = ITEMS.registerSimpleItem("tungsten_ingot");
    public static final DeferredItem<Item> TUNGSTEN_NUGGET = ITEMS.registerSimpleItem("tungsten_nugget");

    public static final DeferredItem<Item> PRISMATIC_INGOT =
            ITEMS.registerItem("prismatic_ingot", key ->
                    new Item(new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Gemology.MODID, "prismatic_ingot")))
                            .trimMaterial(ModTrimMaterials.PRISMATIC)
                    )
            );


    public static final DeferredItem<Item> WHITE_CHROMA = registerChroma("white_chroma");
    public static final DeferredItem<Item> LIGHT_GRAY_CHROMA = registerChroma("light_gray_chroma");
    public static final DeferredItem<Item> GRAY_CHROMA = registerChroma("gray_chroma");
    public static final DeferredItem<Item> BLACK_CHROMA = registerChroma("black_chroma");
    public static final DeferredItem<Item> PINK_CHROMA = registerChroma("pink_chroma");
    public static final DeferredItem<Item> RED_CHROMA = registerChroma("red_chroma");
    public static final DeferredItem<Item> ORANGE_CHROMA = registerChroma("orange_chroma");
    public static final DeferredItem<Item> YELLOW_CHROMA = registerChroma("yellow_chroma");
    public static final DeferredItem<Item> LIME_CHROMA = registerChroma("lime_chroma");
    public static final DeferredItem<Item> GREEN_CHROMA = registerChroma("green_chroma");
    public static final DeferredItem<Item> CYAN_CHROMA = registerChroma("cyan_chroma");
    public static final DeferredItem<Item> LIGHT_BLUE_CHROMA = registerChroma("light_blue_chroma");
    public static final DeferredItem<Item> BLUE_CHROMA = registerChroma("blue_chroma");
    public static final DeferredItem<Item> MAGENTA_CHROMA = registerChroma("magenta_chroma");
    public static final DeferredItem<Item> PURPLE_CHROMA = registerChroma("purple_chroma");
    public static final DeferredItem<Item> BROWN_CHROMA = registerChroma("brown_chroma");
    public static final DeferredItem<Item> PRISMATIC_CHROMA = registerChroma("prismatic_chroma");

    public static final DeferredItem<Item> CHROMA_CATALYST = ITEMS.registerItem("chroma_catalyst", ChromaCatalystItem::new);

    public static final DeferredHolder<Item, FusionItem> FUSION = ITEMS.registerItem("fusion", FusionItem::new);

    public static final DeferredItem<Item> WHITE_ESSENCE = ITEMS.registerSimpleItem("white_essence", properties -> (properties.stacksTo(1)));
    public static final DeferredItem<Item> YELLOW_ESSENCE = ITEMS.registerSimpleItem("yellow_essence", properties -> (properties.stacksTo(1)));
    public static final DeferredItem<Item> BLUE_ESSENCE = ITEMS.registerSimpleItem("blue_essence", properties -> (properties.stacksTo(1)));
    public static final DeferredItem<Item> PINK_ESSENCE = ITEMS.registerSimpleItem("pink_essence", properties -> (properties.stacksTo(1)));


    // ----------- GEMS ------------

    public static final DeferredItem<Item> INACTIVE_RUBY_BASE = registerGemBase("inactive_ruby_base");
    public static final DeferredItem<Item> INACTIVE_JASPER_BASE = registerGemBase("inactive_jasper_base");
    public static final DeferredItem<Item> INACTIVE_QUARTZ_BASE = registerGemBase("inactive_quartz_base");
    public static final DeferredItem<Item> INACTIVE_AGATE_BASE = registerGemBase("inactive_agate_base");

    public static final DeferredItem<Item> RUBY = registerGemItem("ruby", Identifier.fromNamespaceAndPath(Gemology.MODID, "ruby"));
    public static final DeferredItem<Item> BLUE_RUBY = registerGemItem("blue_ruby", Identifier.fromNamespaceAndPath(Gemology.MODID, "blue_ruby"));
    public static final DeferredItem<Item> JASPER = registerGemItem("jasper", Identifier.fromNamespaceAndPath(Gemology.MODID, "jasper"));
    public static final DeferredItem<Item> QUARTZ = registerGemItem("quartz", Identifier.fromNamespaceAndPath(Gemology.MODID, "quartz"));
    public static final DeferredItem<Item> AGATE = registerGemItem("agate", Identifier.fromNamespaceAndPath(Gemology.MODID, "agate"));

    // ----------- BLOCK ITEMS -----------


    public static final DeferredItem<BlockItem> TUNGSTEN_ORE= ITEMS.registerSimpleBlockItem("tungsten_ore", ModBlocks.TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TUNGSTEN_ORE = ITEMS.registerSimpleBlockItem("deepslate_tungsten_ore", ModBlocks.DEEPSLATE_TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> TUNGSTEN_BLOCK = ITEMS.registerSimpleBlockItem("tungsten_block", ModBlocks.TUNGSTEN_BLOCK);
    public static final DeferredItem<BlockItem> RAW_TUNGSTEN_BLOCK = ITEMS.registerSimpleBlockItem("raw_tungsten_block", ModBlocks.RAW_TUNGSTEN_BLOCK);

    public static final DeferredItem<BlockItem> RUINED_MARBLE_BLOCK = ITEMS.registerSimpleBlockItem("ruined_marble_block", ModBlocks.RUINED_MARBLE_BLOCK);
    public static final DeferredItem<BlockItem> RUINED_MARBLE_BRICK = ITEMS.registerSimpleBlockItem("ruined_marble_brick", ModBlocks.RUINED_MARBLE_BRICK);
    public static final DeferredItem<BlockItem> RUINED_MARBLE_PILLAR = ITEMS.registerSimpleBlockItem("ruined_marble_pillar", ModBlocks.RUINED_MARBLE_PILLAR);
    public static final DeferredItem<BlockItem> CHISELED_RUINED_MARBLE = ITEMS.registerSimpleBlockItem("chiseled_ruined_marble", ModBlocks.CHISELED_RUINED_MARBLE);
    public static final DeferredItem<BlockItem> SMOOTH_RUINED_MARBLE = ITEMS.registerSimpleBlockItem("smooth_ruined_marble", ModBlocks.SMOOTH_RUINED_MARBLE);

    public static final DeferredItem<BlockItem> DRAINED_STONE = ITEMS.registerSimpleBlockItem("drained_stone", ModBlocks.DRAINED_STONE);
    public static final DeferredItem<BlockItem> DRAINED_SOIL = ITEMS.registerSimpleBlockItem("drained_soil", ModBlocks.DRAINED_SOIL);

    public static final DeferredItem<BlockItem> INCUBATOR = ITEMS.registerSimpleBlockItem("incubator", ModBlocks.INCUBATOR);


    public static final DeferredItem<BlockItem> WHITE_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("white_chroma_crystal", ModBlocks.WHITE_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> LIGHT_GRAY_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("light_gray_chroma_crystal", ModBlocks.LIGHT_GRAY_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> GRAY_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("gray_chroma_crystal", ModBlocks.GRAY_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> BLACK_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("black_chroma_crystal", ModBlocks.BLACK_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> PINK_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("pink_chroma_crystal", ModBlocks.PINK_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> RED_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("red_chroma_crystal", ModBlocks.RED_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> ORANGE_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("orange_chroma_crystal", ModBlocks.ORANGE_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> YELLOW_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("yellow_chroma_crystal", ModBlocks.YELLOW_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> LIME_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("lime_chroma_crystal", ModBlocks.LIME_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> GREEN_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("green_chroma_crystal", ModBlocks.GREEN_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> CYAN_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("cyan_chroma_crystal", ModBlocks.CYAN_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> LIGHT_BLUE_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("light_blue_chroma_crystal", ModBlocks.LIGHT_BLUE_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> BLUE_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("blue_chroma_crystal", ModBlocks.BLUE_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> MAGENTA_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("magenta_chroma_crystal", ModBlocks.MAGENTA_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> PURPLE_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("purple_chroma_crystal", ModBlocks.PURPLE_CHROMA_CRYSTAL);
    public static final DeferredItem<BlockItem> BROWN_CHROMA_CRYSTAL = ITEMS.registerSimpleBlockItem("brown_chroma_crystal", ModBlocks.BROWN_CHROMA_CRYSTAL);


    private static DeferredItem<Item> registerGemItem(String name, Identifier gemId) {
        DeferredItem<Item> item = ITEMS.registerItem(
                name,
                properties -> new ItemGem(properties, gemId)
        );

        GEM_ITEMS.put(gemId, item);

        return item;
    }

    public static ItemGem getGemItem(Identifier gemId) {
        DeferredItem<Item> item = GEM_ITEMS.get(gemId);

        if (item == null) {
            return null;
        }

        return (ItemGem) item.get();
    }

    private static DeferredItem<Item> registerChroma(String name) {
        return ITEMS.registerItem(name, ItemChroma::new);
    }

    private static DeferredItem<Item> registerGemBase(String name) {
        return ITEMS.registerItem(name, ItemGemBase::new);
    }
}
