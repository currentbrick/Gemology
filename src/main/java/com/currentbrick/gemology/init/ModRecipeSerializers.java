package com.currentbrick.gemology.init;

import com.currentbrick.gemology.Gemology;
import com.currentbrick.gemology.recipe.IncubationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, Gemology.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<IncubationRecipe>> INCUBATION =
            RECIPE_SERIALIZERS.register(
                    "incubation",
                    () -> new RecipeSerializer<>(
                            IncubationRecipe.CODEC,
                            IncubationRecipe.STREAM_CODEC
                    )
            );

    public static void register(net.neoforged.bus.api.IEventBus modEventBus) {
        RECIPE_SERIALIZERS.register(modEventBus);
    }
}