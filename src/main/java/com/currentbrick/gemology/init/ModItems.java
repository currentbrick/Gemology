package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.items.FusionItem;
import com.currentbrick.gemology.items.ItemGem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.food.FoodProperties;
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


    public static final DeferredItem<Item> WHITE_CHROMA = ITEMS.registerSimpleItem("white_chroma");
    public static final DeferredItem<Item> LIGHT_GRAY_CHROMA = ITEMS.registerSimpleItem("light_gray_chroma");
    public static final DeferredItem<Item> GRAY_CHROMA = ITEMS.registerSimpleItem("gray_chroma");
    public static final DeferredItem<Item> BLACK_CHROMA = ITEMS.registerSimpleItem("black_chroma");
    public static final DeferredItem<Item> PINK_CHROMA = ITEMS.registerSimpleItem("pink_chroma");
    public static final DeferredItem<Item> RED_CHROMA = ITEMS.registerSimpleItem("red_chroma");
    public static final DeferredItem<Item> ORANGE_CHROMA = ITEMS.registerSimpleItem("orange_chroma");
    public static final DeferredItem<Item> YELLOW_CHROMA = ITEMS.registerSimpleItem("yellow_chroma");
    public static final DeferredItem<Item> LIME_CHROMA = ITEMS.registerSimpleItem("lime_chroma");
    public static final DeferredItem<Item> GREEN_CHROMA = ITEMS.registerSimpleItem("green_chroma");
    public static final DeferredItem<Item> CYAN_CHROMA = ITEMS.registerSimpleItem("cyan_chroma");
    public static final DeferredItem<Item> LIGHT_BLUE_CHROMA = ITEMS.registerSimpleItem("light_blue_chroma");
    public static final DeferredItem<Item> BLUE_CHROMA = ITEMS.registerSimpleItem("blue_chroma");
    public static final DeferredItem<Item> MAGENTA_CHROMA = ITEMS.registerSimpleItem("magenta_chroma");
    public static final DeferredItem<Item> PURPLE_CHROMA = ITEMS.registerSimpleItem("purple_chroma");
    public static final DeferredItem<Item> BROWN_CHROMA = ITEMS.registerSimpleItem("brown_chroma");
    public static final DeferredItem<Item> PRISMATIC_CHROMA = ITEMS.registerSimpleItem("prismatic_chroma");


    public static final DeferredHolder<Item, FusionItem> FUSION = ITEMS.registerItem("fusion", FusionItem::new);


    // ----------- GEMS ------------

    public static final DeferredItem<Item> RUBY = registerGemItem("ruby", Identifier.fromNamespaceAndPath(Gemology.MODID, "ruby"));
    public static final DeferredItem<Item> JASPER = registerGemItem("jasper", Identifier.fromNamespaceAndPath(Gemology.MODID, "jasper"));


    // ----------- BLOCK ITEMS -----------


    public static final DeferredItem<BlockItem> TUNGSTEN_ORE= ITEMS.registerSimpleBlockItem("tungsten_ore", ModBlocks.TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TUNGSTEN_ORE = ITEMS.registerSimpleBlockItem("deepslate_tungsten_ore", ModBlocks.DEEPSLATE_TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> TUNGSTEN_BLOCK = ITEMS.registerSimpleBlockItem("tungsten_block", ModBlocks.TUNGSTEN_BLOCK);


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
}
