package com.currentbrick.gempire.init;

import com.currentbrick.gempire.Gemology;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Gemology.MODID);


    // ----------- ITEMS -----------

    public static final DeferredItem<Item> RAW_TUNGSTEN = ITEMS.registerSimpleItem("raw_tungsten", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    public static final DeferredItem<Item> TUNGSTEN_INGOT = ITEMS.registerSimpleItem("tungsten_ingot", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    public static final DeferredItem<Item> TUNGSTEN_NUGGET = ITEMS.registerSimpleItem("tungsten_nugget", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));


    // ----------- BLOCK ITEMS -----------


    public static final DeferredItem<BlockItem> TUNGSTEN_ORE= ITEMS.registerSimpleBlockItem("tungsten_ore", ModBlocks.TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TUNGSTEN_ORE = ITEMS.registerSimpleBlockItem("deepslate_tungsten_ore", ModBlocks.DEEPSLATE_TUNGSTEN_ORE);
}
