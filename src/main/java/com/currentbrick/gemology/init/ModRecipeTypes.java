package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.recipe.IncubationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, Gemology.MODID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<IncubationRecipe>> INCUBATION =
            RECIPE_TYPES.register(
                    "incubation",
                    () -> RecipeType.simple(
                            net.minecraft.resources.Identifier.fromNamespaceAndPath(
                                    Gemology.MODID,
                                    "incubation"
                            )
                    )
            );

    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
    }
}