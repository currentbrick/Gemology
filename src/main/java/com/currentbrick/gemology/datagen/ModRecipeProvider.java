package com.currentbrick.gemology.datagen;

import com.currentbrick.gemology.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.MultiRegistryBootstrap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.Set;

public class ModRecipeProvider extends RecipeProvider {


    protected ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);

    }

    @Override
    protected void buildRecipes() {
        HolderGetter<Item> items = output.lookup(Registries.ITEM);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.TUNGSTEN_INGOT.get())
                .requires(ModItems.TUNGSTEN_NUGGET.get(), 9)
                .unlockedBy("has_tungsten_nugget", has(ModItems.TUNGSTEN_NUGGET.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.TUNGSTEN_NUGGET.get(), 9)
                .requires(ModItems.TUNGSTEN_INGOT.get(), 1)
                .unlockedBy("has_tungsten_ingot", has(ModItems.TUNGSTEN_INGOT.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.TUNGSTEN_BLOCK.get())
                .requires(ModItems.TUNGSTEN_INGOT.get(), 9)
                .unlockedBy("has_tungsten_ingot", has(ModItems.TUNGSTEN_INGOT.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.RAW_TUNGSTEN_BLOCK.get())
                .requires(ModItems.RAW_TUNGSTEN.get(), 9)
                .unlockedBy("has_raw_tungsten", has(ModItems.RAW_TUNGSTEN.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, ModItems.RAW_TUNGSTEN.get(), 9)
                .requires(ModItems.RAW_TUNGSTEN_BLOCK.get())
                .unlockedBy("has_raw_tungsten_block", has(ModItems.RAW_TUNGSTEN_BLOCK.get()))
                .save(output);


        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_TUNGSTEN.get()),
                RecipeCategory.MISC,
                CookingBookCategory.MISC,
                ModItems.TUNGSTEN_INGOT.get(),
                0.7f,
                200)
                .unlockedBy("has_raw_tungsten", has(ModItems.RAW_TUNGSTEN.get()))
                .save(output, "gemology:raw_tungsten_smelting");

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_TUNGSTEN.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.TUNGSTEN_INGOT.get(),
                        0.7f,
                        100)
                .unlockedBy("has_raw_tungsten", has(ModItems.RAW_TUNGSTEN.get()))
                .save(output, "gemology:raw_tungsten_blasting");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.TUNGSTEN_ORE.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.TUNGSTEN_INGOT.get(),
                        0.7f,
                        200)
                .unlockedBy("has_tungsten_ore", has(ModItems.TUNGSTEN_ORE.get()))
                .save(output, "gemology:tungsten_ore_smelting");

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.TUNGSTEN_ORE.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.TUNGSTEN_INGOT.get(),
                        0.7f,
                        100)
                .unlockedBy("has_tungsten_ore", has(ModItems.TUNGSTEN_ORE.get()))
                .save(output, "gemology:tungsten_ore_blasting");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.DEEPSLATE_TUNGSTEN_ORE.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.TUNGSTEN_INGOT.get(),
                        0.7f,
                        200)
                .unlockedBy("has_deepslate_tungsten_ore", has(ModItems.DEEPSLATE_TUNGSTEN_ORE.get()))
                .save(output, "gemology:deepslate_tungsten_ore_smelting");

        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.DEEPSLATE_TUNGSTEN_ORE.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.TUNGSTEN_INGOT.get(),
                        0.7f,
                        100)
                .unlockedBy("has_deepslate_tungsten_ore", has(ModItems.DEEPSLATE_TUNGSTEN_ORE.get()))
                .save(output, "gemology:deepslate_tungsten_ore_blasting");

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.INCUBATOR.get())
                .pattern("TIT")
                .pattern("DBD")
                .pattern("TIT")
                .define('T', ModItems.TUNGSTEN_BLOCK.get())
                .define('I', Items.IRON_INGOT)
                .define('B', Items.BLAZE_ROD)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_tungsten_ingot", has(ModItems.TUNGSTEN_INGOT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.FUSION.get())
                .pattern("TWT")
                .pattern("YCB")
                .pattern("TPT")
                .define('T', ModItems.TUNGSTEN_BLOCK.get())
                .define('W', ModItems.WHITE_ESSENCE.get())
                .define('B', ModItems.BLUE_ESSENCE.get())
                .define('Y', ModItems.YELLOW_ESSENCE.get())
                .define('P', ModItems.PINK_ESSENCE.get())
                .define('C', ModItems.PRISMATIC_CHROMA.get())
                .unlockedBy("has_tungsten_ingot", has(ModItems.TUNGSTEN_INGOT.get()))
                .save(output);
    }

    public static MultiRegistryBootstrap create() {
        return new MultiRegistryBootstrap() {
            @Override
            public Set<ResourceKey<? extends Registry<?>>> requestedRegistries() {
                return Set.of(
                        Registries.RECIPE,
                        Registries.ADVANCEMENT
                );
            }

            @Override
            public void run(MultiRegistryBootstrap.BootstrapGetter registries) {
                new ModRecipeProvider(
                        registries.get(Registries.RECIPE),
                        registries.get(Registries.ADVANCEMENT)
                ).buildRecipes();
            }
        };
    }
}
